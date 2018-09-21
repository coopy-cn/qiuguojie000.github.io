package com.orye.business.util.httpsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WxModelUtil {
	public static Map<String,List<String>> map = new HashMap<>();
	
	static{
		List<String> andList = new ArrayList<>();
		//小哇手机
		andList.add("Dalvik/1.6.0 (Linux; U; Android 4.4.4; WA1 Build/KTU84P)");
		//红米 NOTE4 高配
		andList.add("Mozilla/5.0 (Linux; Android 6.0; Redmi Note 4 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 Mobile Safari/537.36");
		//小哇手机
		andList.add("Mozilla/5.0 (Linux; Android 4.4.4; WA1 Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36");
		map.put("1", andList);
		
		
		
		List<String> iponeList = new ArrayList<>();
		iponeList.add("Mozilla/5.0 (iPhone; CPU iPhone OS 9_3_5 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Mobile/13G36");
		map.put("2", iponeList);
	}
	
	/**
	 * 根据客户端类型随机获取机型agent
	 * @param appType	客户端类型1安卓2ios
	 * @return
	 */
	public static String getModelAgent(String appType){
		List<String> list = map.get(appType);
		if(list!=null&&list.size()>0){
			int radSize = list.size();
			Random r = new Random();
			int rInt = r.nextInt(radSize);
			return list.get(rInt);
		}
		return "";
	}
}
