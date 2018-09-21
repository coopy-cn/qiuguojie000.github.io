package com.core.teamwork.base.cache.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.core.teamwork.base.cache.annotations.AcuquireLock;
import com.core.teamwork.base.cache.lock.Lock;
import com.core.teamwork.base.cache.lock.RedisBasedDistributedLock;
import com.core.teamwork.base.cache.utils.AopUtils;
import com.core.teamwork.base.util.returnback.BaseMsgTips;
import com.core.teamwork.base.util.returnback.BaseParameterEunm;
import com.core.teamwork.base.util.returnback.ReMessage;

/**
 * Created by cyl on 2016/09/23.
 * <p>
 * lock aspect used to intercept method which has @AcquireLock annotation on it.
 * Then do the cache job. Note: methods which are intercepted must not have
 * primitive type arguments.
 */

// @Component
public class AcquireLockAspect {
	private static final String NAMESPACE_SPLIT = "_";
	private static final String KEY_SPLIT = ":";

	private static final Logger logger = LoggerFactory
			.getLogger(AcquireLockAspect.class);

	/**
	 * Acquire lock.
	 * 
	 * @param jedis
	 * @return true if lock is acquired, false acquire timeouted
	 * @throws InterruptedException
	 *             in case of thread interruption
	 */
	public Object acquire(ProceedingJoinPoint pjp) throws Throwable {

		Object retObj = null;
		Method method = AopUtils.getMethod(pjp);
		assert method != null;
		AcuquireLock lock = method.getAnnotation(AcuquireLock.class);
		String namespace = lock.namespace();
		String[] fieldsKey = lock.fieldsKey();
		String lockKey = parseKey(namespace, fieldsKey, method, pjp.getArgs());

		// int timeout = lock.timeout();
		Lock redisLock = new RedisBasedDistributedLock(lockKey,
				lock.expire());
		try {
			boolean bool = redisLock.tryLock();
			if (bool) {
				retObj = pjp.proceed();
				redisLock.unlock();
				return retObj;
			}
		} catch (Exception e) {
			logger.error("AcquireLockAspect==>" + lockKey + "异常报错", e);
			redisLock.unlock();
		}

		// while (timeout >= 0) {
		// try {
		// long expires = System.currentTimeMillis() + lock.expire()*1000 + 1;
		// String expiresStr = String.valueOf(expires); //锁到期时间
		// if (RedisDBUtil.redisDao.setnx(lockKey, expiresStr)) {
		// // lock acquired
		// retObj = pjp.proceed();
		// RedisDBUtil.redisDao.delete(lockKey);
		// return retObj;
		// }else{
		// String currentValueStr = RedisDBUtil.redisDao.get(lockKey);
		// //redis里的时间
		// if (currentValueStr != null && Long.parseLong(currentValueStr) <
		// System.currentTimeMillis()) {
		// //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
		// // lock is expired
		// String oldValueStr = RedisDBUtil.redisDao.getset(lockKey,
		// expiresStr);
		// //获取上一个锁到期时间，并设置现在的锁到期时间，
		// //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
		// if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
		// //如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
		// // lock acquired
		// retObj = pjp.proceed();
		// RedisDBUtil.redisDao.delete(lockKey);
		// return retObj;
		// }
		// }
		// }
		//
		//
		// timeout -= 100;
		// Thread.sleep(100);
		// }catch (Exception e) {
		// logger.error("AcquireLockAspect==>"+lockKey+"异常报错",e);
		// }
		//
		// }
		return retObj;
	}

	/**
	 * Parse key and build a redis key with namespace. The key's definition is
	 * support the SpEL Expression
	 */
	private String parseKey(String namespace, String[] fieldsKey,
			Method method, Object[] args) {
		StringBuilder sb = new StringBuilder();
		/**
		 * Get method parameters using the spring support library.
		 */
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paramNameArray = u.getParameterNames(method);
		/**
		 * Put all the parameters into SpEL context and analysis key using SpEL
		 */
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < paramNameArray.length; i++) {
			context.setVariable(paramNameArray[i], args[i]);
		}

		sb.append(namespace).append(NAMESPACE_SPLIT);
		for (String key : fieldsKey) {
			String value = parser.parseExpression(key).getValue(context,
					String.class);
			sb.append(value).append(KEY_SPLIT);
		}
		String fullKey = sb.toString();
		int index;
		if (fullKey.length() > 0 && (index = fullKey.lastIndexOf(":")) > 0) {
			fullKey = fullKey.substring(0, index);
		}
		return fullKey;
	}

	public void doException(Exception ex) {
		logger.error(ex.getLocalizedMessage());
	}
}
