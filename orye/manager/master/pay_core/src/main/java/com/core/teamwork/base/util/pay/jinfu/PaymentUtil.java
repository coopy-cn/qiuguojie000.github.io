package com.core.teamwork.base.util.pay.jinfu;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentUtil {
	
//	/**
//	 * 参数加密
//	 * @param map
//	 * @param keyValue  商户密钥
//	 * @return
//	 * @throws Exception 
//	 */
//	public static String getSign(Map<String,Object> map,String keyValue) throws Exception{
//		//密文
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("keyValue=" + keyValue);
//		
//		List<String> keys = new ArrayList<String>(map.keySet());
//		//排序
//        Collections.sort(keys);
//		//参数值拼接进行加密
//		for(String key :keys){
//			
//			if(!"sign".equals(key)&&!"keyValue".equals(key)){
//				String value = map.get(key)==null?"":map.get(key).toString();
//				buffer.append("&"+key + "=" + value);
//			}
//		}
//		
//		String sNewString = getSign(buffer.toString().toUpperCase(), "MD5");
//		
//		return sNewString;
//	}
	
	/**
	 * 参数加密
	 * @param map
	 * @param keyValue  商户密钥
	 * @return
	 * @throws Exception 
	 */
	public static String getSign(Map<String,Object> map,String keyValue) throws Exception{
		StringBuffer buffer = new StringBuffer();
		buffer.append("keyValue=" + keyValue+"&");
		buffer.append(getParamStr(map));
		System.out.println(buffer.toString());
		String sNewString = getSign(buffer.toString().toUpperCase(), "MD5");
		
		return sNewString;
	}
	
	/**
	 * 参数签名串拼接
	 * @param map
	 * @return
	 */
	public static String getParamStr(Map<String,Object> map){
		StringBuffer buffer = new StringBuffer();
		List<String> keys = new ArrayList<String>(map.keySet());
		//排序
        Collections.sort(keys);
		//参数值拼接进行加密
        for (int i = 0; i < keys.size(); i++) {
        	String key = keys.get(i);
			if(!"sign".equals(key)&&!"keyValue".equals(key)){
				String value = map.get(key)==null?"":map.get(key).toString();
				if(i==0){
					buffer.append(key + "=" + value);
				}else{
					buffer.append("&"+key + "=" + value);
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 获取加密签名
	 * @param str 字符
	 * @param type 加密类型
	 * @return 
	 * @throws Exception
	 */
	public static String getSign(String str, String type) throws Exception {
		MessageDigest crypt = MessageDigest.getInstance(type);
		crypt.reset();
		crypt.update(str.getBytes("UTF-8"));
		return str = byteToHex(crypt.digest());
	}
	
	/**
	 * 字节转换 16 进制
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	/**
	 * 验签
	 * @param map
	 * @param keyValue  商户密钥
	 * @return
	 * @throws Exception 
	 */
	public static boolean checkSign(Map<String,Object> map,String keyValue) throws Exception{
		if(map.get("sign")==null){
			return false;
		}
		//密文
		String sign = map.get("sign").toString();
		map.remove("sign");
		String sNewString = getSign(map, keyValue);
		
		return sNewString.equals(sign);
	}
	
	public static void main(String[] args) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("ab", "ddd");
		map.put("aa", "bbb");
		map.put("bull", "ccc");
		String s =getSign(map,"Utj0tyhKszqcuHXaPgZ2MkJPutDEcf4IO7cVTfvsQMaBiYhna1RycgEjaPFf5YQA");
		System.out.println(s);
	}
}
