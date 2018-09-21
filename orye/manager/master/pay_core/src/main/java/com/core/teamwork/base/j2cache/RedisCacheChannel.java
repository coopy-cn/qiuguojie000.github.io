package com.core.teamwork.base.j2cache;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import com.core.teamwork.base.j2cache.redis.RedisCacheProvider;
import com.core.teamwork.base.util.ReadPro;
import com.core.teamwork.base.util.redis.RedisDBUtil;

/**
 * 缓存Redis PUB/SUB监听通道
 * @author flyfox 330627517@qq.com
 */
public class RedisCacheChannel extends BinaryJedisPubSub implements CacheExpiredListener, CacheChannel {

	private final static Logger log = LoggerFactory.getLogger(RedisCacheChannel.class);

	private String name;
//	private static String channel = J2Cache.getConfig().getProperty("redis.channel_name");
	private static String channel = ReadPro.getValue("redis.channel_name");
	private final static RedisCacheChannel instance = new RedisCacheChannel("default");
	private final Thread thread_subscribe;

	/**
	 * 单例方法
	 * 
	 * @return 返回 CacheChannel 单实例
	 */
	public final static RedisCacheChannel getInstance() {
		return instance;
	}

	/**
	 * 初始化缓存通道并连接
	 * 
	 * @param name
	 *            : 缓存实例名称
	 */
	private RedisCacheChannel(String name) throws CacheException {
		this.name = name;
		try {
			long ct = System.currentTimeMillis();
			CacheManager.initCacheProvider(this);

			thread_subscribe = new Thread(new Runnable() {
				@Override
				public void run() {
					try (Jedis jedis = RedisCacheProvider.getResource()) {
						jedis.subscribe(RedisCacheChannel.this, SafeEncoder.encode(channel));
					}
				}
			});

			thread_subscribe.start();

			log.info("Connected to channel:" + this.name + ", time " + (System.currentTimeMillis() - ct) + " ms.");

		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	/**
	 * 获取缓存中的数据
	 * 
	 * @param region
	 *            : Cache Region name
	 * @param key
	 *            : Cache key
	 * @return cache object
	 */
	public CacheObject get(String region, Object key) {
		CacheObject obj = new CacheObject();
		obj.setRegion(region);
		obj.setKey(key);
		if (region != null && key != null) {
			obj.setValue(CacheManager.get(LEVEL_1, region, key));
			if (obj.getValue() == null) {
				obj.setValue(CacheManager.get(LEVEL_2, region, key));
				if (obj.getValue() != null) {
					obj.setLevel(LEVEL_2);
					CacheManager.set(LEVEL_1, region, key, obj.getValue());
				}
			} else
				obj.setLevel(LEVEL_1);
		}
		return obj;
	}

	/**
	 * 写入缓存
	 * 
	 * @param region
	 *            : Cache Region name
	 * @param key
	 *            : Cache key
	 * @param value
	 *            : Cache value
	 */
	public void set(String region, Object key, Object value) {
		if (region != null && key != null) {
			if (value == null)
				evict(region, key);
			else {
				// 分几种情况
				// Object obj1 = CacheManager.get(LEVEL_1, region, key);
				// Object obj2 = CacheManager.get(LEVEL_2, region, key);
				// 1. L1 和 L2 都没有
				// 2. L1 有 L2 没有（这种情况不存在，除非是写 L2 的时候失败
				// 3. L1 没有，L2 有
				// 4. L1 和 L2 都有
				_sendEvictCmd(region, key);// 清除原有的一级缓存的内容
				CacheManager.setExpire(LEVEL_1, region, key, value,0);
				CacheManager.set(LEVEL_2, region, key, value);
			}
		}
		// log.info("write data to cache region="+region+",key="+key+",value="+value);
	}

	/**
	 * 删除缓存
	 * 
	 * @param region
	 *            : Cache Region name
	 * @param key
	 *            : Cache key
	 */
	public void evict(String region, Object key) {
		CacheManager.evict(LEVEL_1, region, key); // 删除一级缓存
		CacheManager.evict(LEVEL_2, region, key); // 删除二级缓存
		RedisDBUtil.redisDao.del("lockCache:"+key);
		_sendEvictCmd(region, key); // 发送广播
	}

	/**
	 * 批量删除缓存
	 * 
	 * @param region
	 *            : Cache region name
	 * @param keys
	 *            : Cache key
	 */
	@SuppressWarnings({ "rawtypes" })
	public void batchEvict(String region, List keys) {
		CacheManager.batchEvict(LEVEL_1, region, keys);
		CacheManager.batchEvict(LEVEL_2, region, keys);
		if(null != keys && keys.size() > 0){
			List<String> list = new ArrayList<String>();
			for (Object key:keys) {
				list.add("lockCache:"+key);
			}
			RedisDBUtil.redisDao.del((String[])list.toArray());
		}
		
		_sendEvictCmd(region, keys);
	}

	/**
	 * Clear the cache
	 * 
	 * @param region
	 *            : Cache region name
	 */
	public void clear(String region) throws CacheException {
		CacheManager.clear(LEVEL_1, region);
		CacheManager.clear(LEVEL_2, region);
		Set<String> keys = RedisDBUtil.redisDao.hkeys(region);
		if(null != keys && keys.size() > 0){
			List<String> list = new ArrayList<String>();
			for (Object key:keys) {
				list.add("lockCache:"+key);
			}
			RedisDBUtil.redisDao.del((String[])list.toArray());
		}
		_sendClearCmd(region);
	}

	/**
	 * Get cache region keys
	 * 
	 * @param region
	 *            : Cache region name
	 * @return key list
	 */
	@SuppressWarnings("rawtypes")
	public List keys(String region) throws CacheException {
		return CacheManager.keys(LEVEL_1, region);
	}

	/**
	 * 为了保证每个节点缓存的一致，当某个缓存对象因为超时被清除时，应该通知群组其他成员
	 * 
	 * @param region
	 *            : Cache region name
	 * @param key
	 *            : cache key
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void notifyElementExpired(String region, Object key) {

		log.debug("Cache data expired, region=" + region + ",key=" + key);

//		System.out.println("1级缓存清除通知：Cache data expired, region=" + region + ",key=" + key);
		// 删除二级缓存
		if (key instanceof List)
			CacheManager.batchEvict(LEVEL_2, region, (List) key);
		else
			CacheManager.evict(LEVEL_2, region, key);

		// 发送广播
		_sendEvictCmd(region, key);
	}

	/**
	 * 发送清除缓存的广播命令
	 * 
	 * @param region
	 *            : Cache region name
	 * @param key
	 *            : cache key
	 */
	private void _sendEvictCmd(String region, Object key) {
		// 发送广播
		Command cmd = new Command(Command.OPT_DELETE_KEY, region, key);
		try (Jedis jedis = RedisCacheProvider.getResource()) {
			jedis.publish(SafeEncoder.encode(channel), cmd.toBuffers());
		} catch (Exception e) {
			log.error("Unable to delete cache,region=" + region + ",key=" + key, e);
		}
	}

	/**
	 * 发送清除缓存的广播命令
	 * @param region: Cache region name
	 */
	private void _sendClearCmd(String region) {
		// 发送广播
		Command cmd = new Command(Command.OPT_CLEAR_KEY, region, "");
		try (Jedis jedis = RedisCacheProvider.getResource()) {
			jedis.publish(SafeEncoder.encode(channel), cmd.toBuffers());
		} catch (Exception e) {
			log.error("Unable to clear cache,region=" + region, e);
		}
	}

	/**
	 * 删除一级缓存的键对应内容
	 * @param region : Cache region name
	 * @param key  : cache key
	 */
	@SuppressWarnings("rawtypes")
	protected void onDeleteCacheKey(String region, Object key) {
		if (key instanceof List)
			CacheManager.batchEvict(LEVEL_1, region, (List) key);
		else
			CacheManager.evict(LEVEL_1, region, key);
		log.debug("Received cache evict message, region=" + region + ",key=" + key);
	}

	/**
	 * 清除一级缓存的键对应内容
	 * @param region Cache region name
	 */
	protected void onClearCacheKey(String region){
		CacheManager.clear(LEVEL_1, region);
		log.debug("Received cache clear message, region="+region);
	}
	
	/**
	 * 消息接收
	 * 
	 * @param channel 缓存 Channel
	 * @param message 接收到的消息
	 */
	@Override
	public void onMessage(byte[] channel, byte[] message) {
		// 无效消息
		if (message != null && message.length <= 0) {
			log.warn("Message is empty.");
			return;
		}
		try {
			Command cmd = Command.parse(message);

			if (cmd == null || cmd.isLocalCommand())
				return;

//			System.out.println("二级缓存清除通知：Cache data expired, region=" + cmd.getRegion() + ",key=" + cmd.getKey());
			switch (cmd.getOperator()) {
			case Command.OPT_DELETE_KEY:
				onDeleteCacheKey(cmd.getRegion(), cmd.getKey());
				break;
			case Command.OPT_CLEAR_KEY:
				onClearCacheKey(cmd.getRegion());
				break;
			default:
				log.warn("Unknown message type = " + cmd.getOperator());
			}
		} catch (Exception e) {
			log.error("Unable to handle received msg", e);
		}
	}

	/**
	 * 关闭到通道的连接
	 */
	public void close() {
		CacheManager.shutdown(LEVEL_1);
		if(isSubscribed()){
			this.unsubscribe();
		}
		CacheManager.shutdown(LEVEL_2);
		//thread_subscribe.stop();
	}

	@Override
	public void setExpire(String region, Object key, Object value, Integer expire) {
		// TODO Auto-generated method stub
		if (region != null && key != null) {
			if (value == null)
				evict(region, key);
			else {
				// 分几种情况
				// Object obj1 = CacheManager.get(LEVEL_1, region, key);
				// Object obj2 = CacheManager.get(LEVEL_2, region, key);
				// 1. L1 和 L2 都没有
				// 2. L1 有 L2 没有（这种情况不存在，除非是写 L2 的时候失败
				// 3. L1 没有，L2 有
				// 4. L1 和 L2 都有
				_sendEvictCmd(region, key);// 清除原有的一级缓存的内容
				CacheManager.setExpire(LEVEL_1, region, key, value,expire);
				CacheManager.set(LEVEL_2, region, key, value);
			}
		}
	}

}
