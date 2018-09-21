package com.core.teamwork.base.cache.annotations;

import java.lang.annotation.*;

/**
 * Created by cyl on 2016/07/26.
 *
 * Cache synchronized
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheUpdate {
    /*
    A Logical partition namespace of cache.
 */
    String namespace() default "";
    
    String region() default "dianyou";

    /*
        A set of fields used to build the cache key.
     */
    String[] fieldsKey() default {};

    /*
        The update entity of field
     */
    String valueField() default "";

    /*
        The value of update entity
     */
    @SuppressWarnings("rawtypes")
	Class valueType();

    /**
     *  If use this method, AOP will update fieldsKey with return value. Ignore valueField() and valueField()
     */
    boolean updateRetVal() default false;

    /*
        expire time. Units are seconds.
     */
    int expire() default -1;
}
