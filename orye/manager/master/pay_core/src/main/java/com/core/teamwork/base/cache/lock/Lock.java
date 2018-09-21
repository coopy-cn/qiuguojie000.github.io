package com.core.teamwork.base.cache.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author cyl
 *
 */
public interface Lock {
	
	 /**
	 * 尝试获取锁, 获取不到立即返回, 不阻塞
	 */
	 boolean tryLock();
	  
	 /**
	 * 超时自动返回的阻塞性的获取锁, 不响应中断
	 * 
	 * @param timeout
	 * @param unit
	 * @return {@code true} 若成功获取到锁, {@code false} 若在指定时间内未���取到锁
	  * 
	 */
	 boolean tryLock(TimeUnit unit,long timeout);
	 
	 /**
	 * 释放锁
	 */
	 void unlock();
	 
	 
	 /**
	  *  判断锁是否过期
	 * @return true 过期  false没过期
	 */
	boolean isLocked();
}
