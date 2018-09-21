package com.core.teamwork.base.util.pay.third.xlj.util;

import java.util.Map;

import com.core.teamwork.base.util.encrypt.MD5;
import com.core.teamwork.base.util.pay.PayUtil;

public class XljUtil {
	
	/**
	 * 小辣椒生成签名方法
	 * @param map	请求参数
	 * @param signKeyName	签名参数名
	 * @param secret	密钥
	 * @return
	 */
	public static String getSign(Map<String,Object> map,String secret){
		String requestString = PayUtil.getRequestString(map,"sign");
		return MD5.GetMD5Code(requestString+"&"+secret);
	}
	
	/**
	 * 小辣椒签名验证方法
	 * @param map	请求参数
	 * @param signKeyName	签名参数名
	 * @param secret	密钥
	 * @return
	 */
	public static boolean verify(Map<String,Object> map,String secret){
		if(map.get("sign").toString().equals(getSign(map,secret))){
			return true;
		}
		return false;
	}
}
