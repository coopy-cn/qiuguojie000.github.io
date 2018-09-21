package com.core.teamwork.base.dubbo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

public class DubboServiceFactory {
	private static final Logger LOGGER = Logger.getLogger(DubboServiceFactory.class);
	
	private static Map<Class<?>,Object> cacheMap = new ConcurrentHashMap<>();
	private static ApplicationConfig application;
	private static RegistryConfig registry;
	
	public static void init() throws DocumentException{
		SAXReader reader = new SAXReader();
		Document document = reader.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("spring-dubbo.xml"));
		Element root = document.getRootElement();
		Element applicationElement = root.element("application");
		Element registryElement = root.element("registry");
		application = new ApplicationConfig();
		application.setName(applicationElement.attributeValue("name"));
		LOGGER.info("application name:"+application.getName());
		// 连接注册中心配置
		registry = new RegistryConfig();
		registry.setAddress(registryElement.attributeValue("address"));
		LOGGER.info("registry address:"+registry.getAddress());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> clazz){
		if(cacheMap.get(clazz)==null){
			// 引用远程服务
			ReferenceConfig<T> reference = new ReferenceConfig<T>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
			reference.setApplication(application);
			reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
			reference.setInterface(clazz);
			//reference.setId("circleTestService");
			 
			// 和本地bean一样使用xxxService
			cacheMap.put(clazz,reference.get());// 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
		}
		return (T) cacheMap.get(clazz);
		
	}
}
