package com.core.teamwork.base.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties getProperties(String name){
		InputStream input=  PropertiesUtil.class.getResourceAsStream("/"+name+".properties");
		Properties pro = new Properties();
	    try {
			pro.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return pro;
	}
	
	
	/**
	 * @param name  propertiesName
	 * @param key  propertiesKey
	 * @return
	 */
	public static String getProperty(String name ,String key){
		return getProperty(name, key, null);
	}
	
	public static String getProperty(String name ,String key,String defaultValue){
//		PropertiesUtil util = new PropertiesUtil();
		Properties pro = getProperties(name);
		return pro.getProperty(key, defaultValue);
	}
	
	
//	public static void main(String[] args) {
//		for (int i = 0; i < 100; i++) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(PropertiesUtil.getProperty("xx", "minshengURL", null));
//		}
//		
//	}
}
