package com.core.teamwork.base.cache.aop;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.core.teamwork.base.cache.annotations.CacheUpdate;
import com.core.teamwork.base.cache.annotations.Cacheable;
import com.core.teamwork.base.cache.exceptions.DynamicExpireSettingException;
import com.core.teamwork.base.cache.handler.DynamicExpireHandler;
import com.core.teamwork.base.cache.lock.Lock;
import com.core.teamwork.base.cache.lock.RedisBasedDistributedLock;
import com.core.teamwork.base.cache.utils.AopUtils;
import com.core.teamwork.base.j2cache.CacheChannel;
import com.core.teamwork.base.j2cache.CacheObject;
import com.core.teamwork.base.j2cache.J2Cache;

/**
 * Created by cyl on 2016/07/26.
 * <p>
 * Cache aspect used to intercept method which has @Cacheable annotation on it.
 * Then do the cache job.
 * Note: methods which are intercepted must not have primitive type arguments.
 */

//@Component
public class CacheAspect {
    private static final String NAMESPACE_SPLIT = "_";
    private static final String KEY_SPLIT = ":";

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

//    @Autowired
//    private StringCache<Object> cacheStorage;

    //========================================================================
    //                          Cache storage AOP
    //========================================================================

    public Object cache(ProceedingJoinPoint pjp) throws Throwable {

    	Object retObj = null;
        Method method = AopUtils.getMethod(pjp);
        assert method != null;
        Cacheable cacheable = method.getAnnotation(Cacheable.class);

        /**
         * The cacheKey is the full name of redis cache key
         */
        String namespace = cacheable.namespace();
        String region = cacheable.region();
        String[] fieldsKey = cacheable.fieldsKey();
        String cacheKey = parseKey(namespace, fieldsKey, method, pjp.getArgs());
//        Class<?> returnType = ((MethodSignature) pjp.getSignature()).getReturnType();
        Class<? extends DynamicExpireHandler>[] handlers = cacheable.dynamicExpireHandler();

        try {
//            retObj = cacheStorage.get(cacheKey);
        	CacheChannel cache = J2Cache.getChannel();
        	CacheObject cacheObj = cache.get(region, cacheKey);
        	if(null != cacheObj){
        		retObj = cacheObj.getValue();
        	}
            if (retObj == null) {
            	int expire = cacheable.expire();
            	Lock redisLock = new RedisBasedDistributedLock("lockCache:"+cacheKey, expire);
	        	boolean bool = redisLock.tryLock(TimeUnit.SECONDS,10);
	        	
	        	if(bool){
	        		
	        		cacheObj = cache.get(region, cacheKey);
		        	if(null != cacheObj){
		        		retObj = cacheObj.getValue();
		        	}
	        		if(retObj == null){
	        			try {
		                    retObj = pjp.proceed();
		                    // Not cache Null object
		                    if (retObj != null) {
		                        if (expire > 0) {
//		                            cacheStorage.setEx(cacheKey, retObj, expire);
//		                            cache.set("dianyou", cacheKey, retObj);
		                            cache.setExpire(region, cacheKey, retObj, expire);
		                        } else if (handlers.length == 0) {
//		                            cacheStorage.set(cacheKey, retObj);
		                        	cache.set(region, cacheKey, retObj);
		                        } else {
		                            Class<? extends DynamicExpireHandler> handler = handlers[0];
		                            String expireFieldName = cacheable.dynamicExpireFields()[0];
		                            String expireFieldFormat = cacheable.dynamicExpireFieldFormat()[0];
		                            if (StringUtils.isEmpty(expireFieldName) || StringUtils.isEmpty(expireFieldFormat)) {
		                                throw new DynamicExpireSettingException();
		                            }
		                            String expireFieldValue = getArgValue(expireFieldName, String.class, method, pjp.getArgs());
		                            Date dateArg = (new SimpleDateFormat(expireFieldFormat)).parse(expireFieldValue);
		                            long dynamicExpire = handler.newInstance().handler(dateArg);
//		                            cacheStorage.setEx(cacheKey, retObj, dynamicExpire);
		                            cache.setExpire(region, cacheKey, retObj, (int)dynamicExpire);
		                        }
		                    }
		                    redisLock.unlock();
		                } catch (Throwable e) {
		                	redisLock.unlock();
		                	logger.error("CacheAspect==>"+cacheKey+"异常报错",e);
		                }
	        		}else{
	        			redisLock.unlock();
	        		}
	        		
	        	}
            } else {
                logger.debug("Get " + cacheKey + " from cache.");
//                System.out.println("缓存："+cacheObj.getLevel()+"级==>Region:"+cacheObj.getRegion()+"==>key:"+cacheObj.getKey());
//                System.out.println("Get " + cacheKey + " from cache.");
            }
        } catch (Exception e) {
        	logger.error("CacheAspect==>"+cacheKey+"异常报错",e);
        }
        return retObj;
    }

    /**
     * Get arg value
     *
     * @param fieldName arg field name
     * @param argType   arg type
     * @param method    aop method
     * @param args      ProceedingJoinPoint
     * @param <T>       return type
     * @return value
     */
    private <T> T getArgValue(String fieldName, Class<T> argType, Method method, Object[] args) {
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
        return parser.parseExpression(fieldName).getValue(context, argType);
    }


    /**
     * Parse key and build a redis key with namespace.
     * The key's definition is support the SpEL Expression
     */
    private String parseKey(String namespace, String[] fieldsKey, Method method, Object[] args) {
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
            String value = parser.parseExpression(key).getValue(context, String.class);
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


    //========================================================================
    //                          Cache Update AOP
    //========================================================================


    @SuppressWarnings("rawtypes")
    public void cacheUpdate(JoinPoint jp, Object rtv) {
        Method method = AopUtils.getMethod(jp);
        assert method != null;

        CacheUpdate cacheUpdate = method.getAnnotation(CacheUpdate.class);
        /**
         * The cacheKey is the full name of redis cache key
         */
        String namespace = cacheUpdate.namespace();
        String region = cacheUpdate.region();
        String[] fieldsKey = cacheUpdate.fieldsKey();
        String cacheKey = parseKey(namespace, fieldsKey, method, jp.getArgs());
        
        CacheChannel cache = J2Cache.getChannel();

        Class type = cacheUpdate.valueType();
        int expire = cacheUpdate.expire();
        boolean updateRetVal = cacheUpdate.updateRetVal();

        Object value;
        if (updateRetVal) {
            value = rtv;
        } else {
            String valueField = cacheUpdate.valueField();
            value = getUpdateFieldValue(valueField, method, jp, type);
        }

        if (expire > 0) {
//            cacheStorage.setEx(cacheKey, value, expire);
        	cache.setExpire(region, cacheKey, value, expire);
        } else {
//            cacheStorage.set(cacheKey, value);
            cache.set(region, cacheKey, value);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getUpdateFieldValue(String valueField, Method method, JoinPoint jp, Class valueType) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNameArray = u.getParameterNames(method);
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNameArray.length; i++) {
            context.setVariable(paramNameArray[i], jp.getArgs()[i]);
        }
        return parser.parseExpression(valueField).getValue(context, valueType);
    }

}
