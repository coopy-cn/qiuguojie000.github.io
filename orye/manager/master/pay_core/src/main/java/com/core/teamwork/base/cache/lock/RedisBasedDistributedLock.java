package com.core.teamwork.base.cache.lock;

import java.util.concurrent.TimeUnit;

import com.core.teamwork.base.j2cache.CacheChannel;
import com.core.teamwork.base.j2cache.CacheObject;
import com.core.teamwork.base.j2cache.J2Cache;
import com.core.teamwork.base.util.redis.RedisDBUtil;

/**
 * <pre>
 * 基于Redis的SETNX操作实现的分布式锁
 * 
 * 获取锁时最好用lock(long time, TimeUnit unit), 以免网路问题而导致线程一直阻塞
 * 
 * <a href="http://redis.io/commands/setnx">SETNC操作参考资料</a>
 * </pre>
 * 
 * @author cyl
 *
 */
public class RedisBasedDistributedLock extends AbstractLock {

	// 锁的名字
	protected String lockKey;

	// 锁的有效时长(秒)
	protected long lockExpires;

	public RedisBasedDistributedLock(String lockKey,long lockExpires) {
		this.lockKey = lockKey;
		this.lockExpires = lockExpires;
	}

	// 阻塞式获取锁的实现 useTimeout 是否有时间限制     timeout 单位秒
	protected boolean lock(TimeUnit unit,long timeout) {

		long start = System.currentTimeMillis();
		long end = unit.toMillis(timeout); // if !useTimeout, then it's useless

		while (isTimeout(start, end)) {

			long lockExpireTime = System.currentTimeMillis() + lockExpires*1000 + 1;// 锁超时时间
			String stringOfLockExpireTime = String.valueOf(lockExpireTime);

			if (RedisDBUtil.redisDao.setnx(lockKey, stringOfLockExpireTime)) { // 获取到锁
				// TODO 成功获取到锁, 设置相关标识
				return true;
			}else{
				String value = RedisDBUtil.redisDao.get(lockKey);
				if (value != null && isTimeExpired(value)) { // lock is expired
					String oldValue = RedisDBUtil.redisDao.getset(lockKey,stringOfLockExpireTime); // getset
					// is
					// atomic
					// 但是走到这里时每个线程拿到的oldValue肯定不可能一样(因为getset是原子性的)
					// 加入拿到的oldValue依然是expired的，那么就说明拿到锁了
					if (oldValue != null && oldValue.equals(value)) {
						return true;
					}
				}
			}
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean tryLock() {
		long lockExpireTime = System.currentTimeMillis() + lockExpires*1000 + 1;// 锁超时时间
		String stringOfLockExpireTime = String.valueOf(lockExpireTime);

		if (RedisDBUtil.redisDao.setnx(lockKey, stringOfLockExpireTime)) { // 获取到锁
			// TODO 成功获取到锁, 设置相关标识
			return true;
		}else{
			String value = RedisDBUtil.redisDao.get(lockKey);
			if (value != null && isTimeExpired(value)) { // lock is expired
				String oldValue = RedisDBUtil.redisDao.getset(lockKey,stringOfLockExpireTime); // getset
				// is
				// atomic
				// 但是走到这里时每个线程拿到的oldValue肯定不可能一样(因为getset是原子性的)
				// 加入拿到的oldValue依然是expired的，那么就说明拿到锁了
				if (oldValue != null && oldValue.equals(value)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Queries if this lock is held by any thread.
	 * 
	 * @return {@code true} if any thread holds this lock and {@code false}
	 *         otherwise
	 */
	public boolean isLocked0() {
			String value = RedisDBUtil.redisDao.get(lockKey);
			// TODO 这里其实是有问题的, 想:当get方法返回value后, 假设这个value已经是过期的了,
			// 而就在这瞬间, 另一个节点set了value, 这时锁是被别的线程(节点持有), 而接下来的判断
			// 是检测不出这种情况的.不过这个问题应该不会导致其它的问题出现, 因为这个方法的目的本来就
			// 不是同步控制, 它只是一种锁状态的报告.
			return !isTimeExpired(value);
	}

	@Override
	protected void unlock0() {
		// TODO 判断锁是否过期
//		String value = RedisDBUtil.redisDao.get(lockKey);
//		if (!isTimeExpired(value)) {
//			doUnlock();
//		}
		RedisDBUtil.redisDao.delete(lockKey);
	}

	private boolean isTimeExpired(String value) {
		return Long.parseLong(value) < System.currentTimeMillis();
	}

	private boolean isTimeout(long start, long end) {
		return start + end > System.currentTimeMillis();
	}

//	private void doUnlock() {
//		RedisDBUtil.redisDao.delete(lockKey);
//	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 200; i++) {
			Thread t = new Thread(new Runnable(){  
	            public void run(){  
	            	System.out.println("---------------------------------------------------------");
					Object retObj = null;
					CacheChannel cache = J2Cache.getChannel();
			    	CacheObject cacheObj = cache.get("cyl", "fy");
			    	if(null != cacheObj){
			    		retObj = cacheObj.getValue();
			    		System.out.println("第一次缓存直接获取结果："+retObj);
			    	}
			        if (retObj == null) {
			        	RedisBasedDistributedLock lock = new RedisBasedDistributedLock("lockCyl:xx", 60);
						boolean bool = lock.tryLock(TimeUnit.SECONDS,10);
						if(bool){
							cacheObj = cache.get("cyl", "fy");
				        	if(null != cacheObj){
				        		retObj = cacheObj.getValue();
				        		System.out.println("第二次缓存直接获取结果："+retObj);
				        	}
			        		if(retObj == null){
			        			try {
			    					TimeUnit.MILLISECONDS.sleep(2500);
			    				} catch (InterruptedException e) {
			    					// TODO Auto-generated catch block
			    					e.printStackTrace();
			    				}
			        			retObj = "aaaaa";
			        			cache.setExpire("cyl", "fy", retObj, 120);
			        			System.out.println("直接获取结果："+retObj);
			        		}
							lock.unlock();
							System.out.println("---------------------------------------------------------");
						}else{
							System.out.println("--------------------------11-------------------------------");
						}
			        }
			        
			        
	            }});  
	        t.start();  
		}
	}

}
