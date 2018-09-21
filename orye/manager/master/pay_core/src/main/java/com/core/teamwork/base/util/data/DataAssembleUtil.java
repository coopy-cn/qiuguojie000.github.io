/**  
 * @Title: DataAssembleUtil.java
 * @Package com.ijm.gc.util
 * @Description: TODO
 * @author pengzhihao
 * @date 2016-12-28
 */
package com.core.teamwork.base.util.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <P>ClassName: DataAssembleUtil</P>
 * <P>@Description: 数据组合工具类</P>
 * <P>Company:</P>
 * @author pengzhihao
 * @date 2016-12-28下午11:51:24
 */
public class DataAssembleUtil {

	/**
	 * @Description: 组装到page
	 * @param @param tagerData
	 * @param @param orgData
	 * @param @param key
	 * @param @return   
	 * @return Page  
	 * @throws
	 * @author pengzhihao
	 * @date 2016-12-28下午11:53:58
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> dataAssembleByListMap(List<Map<String, Object>> tagerData, List<Map<String, Object>> orgData, 
			String[] keys, String tagerRelationKey, String orgRelationKey){
		
		Map<String, Object> tempMap = new HashMap<String, Object>();
		
		if(orgData != null && orgData.size() > 0){
			for (int i = 0; i < orgData.size(); i++) {
				Map<String, Object> beanOrgMap = orgData.get(i);
				String relationKeyValue = beanOrgMap.get(orgRelationKey).toString();
				tempMap.put(relationKeyValue, beanOrgMap);
			}
		}
		
		for (int i = 0; i < tagerData.size(); i++) {
			Map<String, Object> beanTagerMap = tagerData.get(i);
			String relationKeyValue = beanTagerMap.get(tagerRelationKey) == null ? null : beanTagerMap.get(tagerRelationKey).toString();
			if(relationKeyValue == null || tempMap.get(relationKeyValue) == null){
				for(String key : keys){
					beanTagerMap.put(key, null);
				}
			}else{
				Map<String, Object> beanOrgMap = (Map<String, Object>) tempMap.get(relationKeyValue);
				for(String key : keys){
					beanTagerMap.put(key, beanOrgMap.get(key));
				}
			}
			
			
		}
		
		return tagerData;
	}
}
