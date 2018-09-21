package com.core.teamwork.base.cache.handler;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cyl on 2016/07/26.
 *
 * dynamic expire handler
 */
public interface DynamicExpireHandler extends Serializable {
    long handler(Date date);
}
