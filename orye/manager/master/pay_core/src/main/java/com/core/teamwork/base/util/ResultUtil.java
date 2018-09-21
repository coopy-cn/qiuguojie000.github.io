package com.core.teamwork.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultUtil {
	/**
	 * list按照一个属性值分组成Map
	 * @param <E>
	 * @param list
	 * @param keyName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K,T> Map<K,List<T>> listToMap(List<T> list,Class<K> clazz,String keyName) throws Exception {
		Map<K,List<T>> map = null;
		if(list!=null&&list.size()>0){
			try {
				map = new LinkedHashMap<>();
				for (T t : list) {
					Object key = null;
					if(t instanceof Map){
						Method get = t.getClass().getMethod("get",Object.class);
						key = get.invoke(t,keyName);
					}else{
						Field field = t.getClass().getDeclaredField(keyName);
						field.setAccessible(true);
						key = field.get(t);
					}
					List<T> temp = map.get(key);
					if(temp==null){
						temp = new ArrayList<>();
						map.put((K)key,temp);
					}
					temp.add(t);
				}
				return map;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 对象转换成Map
	 * @param t
	 * @param fileds 保留的字段
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static <T> Map<String,Object> BeanToMap(T t,String[] fileds) throws Exception{
		Map<String,Object> result = new HashMap<>();
		if(fileds!=null&&t!=null){
			for (String filed : fileds) {
				Field f = t.getClass().getDeclaredField(filed);
				f.setAccessible(true);
				result.put(filed,f.get(t));
			}
		}
		return result;
	}
	
	/**
	 * 对象列表转换成Map列表
	 * @param list
	 * @param fileds 保留的字段
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static <T> List<Map<String,Object>> BeanToMapForList(List<T> list,String[] fileds) throws Exception{
		List<Map<String,Object>> result = new ArrayList<>();
		for (T t : list) {
			result.add(BeanToMap(t,fileds));
		}
		return result;
	}
	
}
