package com.core.teamwork.base.cache.lock;

import java.util.concurrent.TimeUnit;

/**
 * 锁的骨架实现, 真正的获取锁的步骤由子类去实现.
 * 
 * @author cyl
 *
 */
public abstract class AbstractLock implements Lock {

	public boolean tryLock(TimeUnit unit,long timeout) {
		return lock(unit,timeout);
	}

	public void unlock() {
		unlock0();
	}

	public boolean isLocked() {
		return isLocked0();
	}

	protected abstract void unlock0();

	protected abstract boolean isLocked0();

	/**
	 * 阻塞式获取锁的实现
	 * 
	 * @param time
	 * @param unit
	 * @return
	 * @throws InterruptedException
	 */
	protected abstract boolean lock(TimeUnit unit,long time);

}
