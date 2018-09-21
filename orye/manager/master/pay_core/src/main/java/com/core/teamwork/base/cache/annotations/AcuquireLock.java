package com.core.teamwork.base.cache.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by cyl on 2016年9月23日 11:12:57.
 * <p/>
 * This annotation used on methods which would be intercepted by spring AOP.
 * Then build keys and storage in a lock which is usually a redis.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AcuquireLock {

	/*
	 * A Logical partition namespace of lock.
	 */
	String namespace() default "";
	/*
	 * A set of fields used to build the cache key.
	 */
	String[] fieldsKey();

	/*
	 * expire time. 锁持有超时，防止线程在入锁以后，无限的执行下去，让锁无法释放 默认10秒
	 */
	int expire() default 10;

	/*
	 * 锁等待超时，防止线程饥饿，永远没有入锁执行代码的机会 默认0秒
	 */
//	int timeout() default 0;

}
