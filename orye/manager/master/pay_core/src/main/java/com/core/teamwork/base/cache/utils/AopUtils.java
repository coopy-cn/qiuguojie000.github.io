package com.core.teamwork.base.cache.utils;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

/**
 * Created by cyl on 2016/07/26.
 */
public class AopUtils {
    /**
     * Get the intercept method object.
     * <p/>
     * MethodSignature.getMethod() The top-level interface or parent class method objects
     * While the cache in the annotation method.
     * Should the object method uses reflection to obtain the current object so.
     */
    @SuppressWarnings("rawtypes")
	public static Method getMethod(JoinPoint pjp) {
//        Object[] args = pjp.getArgs();
//        Class[] argTypes = new Class[pjp.getArgs().length];
//        for (int i = 0; i < args.length; i++) {
//            argTypes[i] = args[i].getClass();
//        }
        //拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * Get method parameters name
     *
     * @param method method object
     * @return parameters name
     */
    public static String[] getParametersName(Method method) {
        if (method == null)
            return null;
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        return u.getParameterNames(method);
    }
}
