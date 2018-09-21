package com.core.teamwork.base.j2cache.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.teamwork.base.j2cache.Cache;
import com.core.teamwork.base.j2cache.CacheException;
import com.core.teamwork.base.j2cache.CacheExpiredListener;
import com.core.teamwork.base.j2cache.CacheProvider;
import com.core.teamwork.base.util.ReadPro;

/**
 * Redis 缓存实现
 * @author Winter Lau
 * @author wendal
 */
public class RedisCacheProvider implements CacheProvider {
	
	private static Pool<Jedis> pool;
	
	protected ConcurrentHashMap<String, RedisCache> caches = new ConcurrentHashMap<>();
	
	public String name() {
		return "redis";
	}
    
	// 这个实现有个问题,如果不使用RedisCacheProvider,但又使用RedisCacheChannel,这就NPE了
    public static Jedis getResource() {
    	return pool.getResource();
    }

	@Override
	public Cache buildCache(String regionName, boolean autoCreate, CacheExpiredListener listener) throws CacheException {
		// 虽然这个实现在并发时有概率出现同一各regionName返回不同的实例
		// 但返回的实例一次性使用,所以加锁了并没有增加收益
		RedisCache cache = caches.get(regionName);
		if (cache == null) {
			cache = new RedisCache(regionName, pool);
			caches.put(regionName, cache);
		}
		return cache;
    }

	@Override
	public void start() throws CacheException {
		JedisPoolConfig config = new JedisPoolConfig();
		
		String host =ReadPro.getValue("redis_host","localhost");
		String sentinelArray =ReadPro.getValue("redis_sentinel_array");
		String masterName =ReadPro.getValue("redis_sentinel_masterName");
		String password = ReadPro.getValue("redis_password");
		
		int port = ReadPro.getValue("redis_port", 6379);
		int timeout = ReadPro.getValue("redis_timeout", 2000);
		int database = ReadPro.getValue("redis_database", 0);
		int sentinelDatabase = ReadPro.getValue("redis_sentinel_database",0);
		
		config.setBlockWhenExhausted(ReadPro.getValue("redis.blockWhenExhausted", true));
		config.setMaxIdle(ReadPro.getValue("redis.maxIdle", 10));
		config.setMinIdle(ReadPro.getValue("redis.minIdle", 5));
//		config.setMaxActive(getProperty(props, "maxActive", 50));
		config.setMaxTotal(ReadPro.getValue("redis.maxTotal", 10000));
		config.setMaxWaitMillis(ReadPro.getValue("redis.maxWait", 1000*5));
		config.setTestWhileIdle(ReadPro.getValue("redis.testWhileIdle", true));
		config.setTestOnBorrow(ReadPro.getValue("redis.testOnBorrow", true));
		config.setTestOnReturn(ReadPro.getValue("redis.testOnReturn", true));
		config.setNumTestsPerEvictionRun(ReadPro.getValue("redis.numTestsPerEvictionRun", 10));
		config.setMinEvictableIdleTimeMillis(ReadPro.getValue("redis.minEvictableIdleTimeMillis", 1000));
		config.setSoftMinEvictableIdleTimeMillis(ReadPro.getValue("redis.softMinEvictableIdleTimeMillis", 10));
		config.setTimeBetweenEvictionRunsMillis(ReadPro.getValue("redis.timeBetweenEvictionRunsMillis", 10));
		config.setLifo(ReadPro.getValue("redis.lifo", false));
		
		if(StringUtils.isNotBlank(sentinelArray)){
			Set<String> sentinels = new HashSet<String>();
            List<JSONObject> sentinelList=JSONArray.parseArray(sentinelArray,JSONObject.class);
            for(JSONObject sentinel:sentinelList){
                sentinels.add(new HostAndPort(sentinel.getString("address"), sentinel.getIntValue("port")).toString());
            }
            pool = new JedisSentinelPool(masterName, sentinels, config, timeout, password,sentinelDatabase);
		}else{
			pool = new JedisPool(config, host, port, timeout, password, database);
		}

		
		
	}

	@Override
	public void stop() {
		pool.destroy();
		caches.clear();
	}
}
