package com.core.teamwork.base.cache.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.core.teamwork.base.cache.handler.DynamicExpireHandler;

/**
 * Created by cyl on 2016年7月28日 12:41:38.
 * <p/>
 * This annotation used on methods which would be intercepted by spring AOP.
 * Then build keys and storage in a cache which is usually a redis.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {

    /*
        A Logical partition namespace of cache.
     */
    String namespace() default "";
    
    String region() default "dianyou";

    /*
        A set of fields used to build the cache key.
     */
    String[] fieldsKey();

    /*
        expire time. Units are seconds.
     */
    int expire() default -1;

    /**
     * dynamic expire setting
     *
     * @return class
     */
    Class<? extends DynamicExpireHandler>[] dynamicExpireHandler() default {};

    /**
     * Dynamic expire field name
     *
     * @return string type
     */
    String[] dynamicExpireFields() default {};

    /**
     * Dynamic expire field format
     *
     * @return date string format
     */
    String[] dynamicExpireFieldFormat() default {};

}
