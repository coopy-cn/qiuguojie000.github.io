package com.core.teamwork.base.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import com.core.teamwork.base.util.ReadPro;


public class SpringDefaultConfigurer implements BeanDefinitionRegistryPostProcessor{
	private String[] keys;
	private static final Map<String,Object> config = new HashMap<String, Object>();

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
	}

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		for (String key : this.keys) {
			config.put(key,ReadPro.getValue(key));
		}
	}
	
	public Object getConfig(String key){
		return config.get(key);
	}
	
}
