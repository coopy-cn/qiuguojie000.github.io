package com.core.teamwork.base.util.pay;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.core.teamwork.base.util.pay.alipay.util.AlipayCore;

public class PayUtil {
	/**
	 * 生成订单号
	 * @return
	 */
	public synchronized static String generateOrderNo(String orderName,String userId,String cpOrderId) {
//		String currentTime = new Date().getTime() + "";
//		// 当前时间毫秒数+三位随机数+用户id
//		return "IWD" + currentTime + UtilDate.getThree() + userId;
		String uuidStr = UUID.randomUUID().toString();
		int hashCodeV = (uuidStr+cpOrderId).hashCode();  
		if(hashCodeV < 0) {//有可能是负数  
		hashCodeV = - hashCodeV;  
		}  
		// 0 代表前面补充0       
		// 4 代表长度为4       
		// d 代表参数为正数型  
		return orderName+userId+String.format("%016d", hashCodeV)+UtilDate.getThree(); 
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	public synchronized static String generateOrderNo(String userId,String cpOrderId) {
		return generateOrderNo("DD", userId, cpOrderId);
	}
	
	/**
	 * 取签名待加签字符串
	 * @param map	请求参数
	 * @param signKeyName	签名参数名
	 * @param secret	密钥
	 * @return
	 */
	public static String getRequestString(Map<String,Object> map,String signKeyName){
		Map<String,Object> tempMap = new HashMap<String, Object>();
		tempMap.putAll(map);
		tempMap.remove(signKeyName);
		return AlipayCore.createLinkString2(tempMap);
	}
	
	/**
	 * 取16进制子串
	 * @param str
	 * @return
	 */
	public static String getHexSting(String str){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			sb.append(Integer.toHexString(str.charAt(i)));
		}
		return sb.toString();
	}
}
