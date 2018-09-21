package com.core.teamwork.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtil implements ApplicationContextAware{

	public static ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringBeanUtil.ctx = applicationContext;
	}

	public static Object getBean(Class<?> clazz){
		return ctx.getBean(clazz);
	}
	
	public static Object getBean(String name){
		return ctx.getBean(name);
	}
}
