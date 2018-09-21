package com.core.teamwork.base.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class AddSHAUtil {
	public static String SHA1(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1"); // 选择SHA-1，也可以选择MD5
			byte[] digest = md.digest(inStr.getBytes()); // 返回的是byet[]，要转化为String存储比较方便
			outStr = bytetoString(digest);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return outStr;
	}
	
	public static String SHA256(String inStr) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("SHA-256"); // 选择SHA-1，也可以选择MD5
            byte[] digest = md.digest(inStr.getBytes()); // 返回的是byet[]，要转化为String存储比较方便
            outStr = bytetoString(digest);
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return outStr;
    }

	public static String bytetoString(byte[] digest) {
		String str = "";
		String tempStr = "";

		for (int i = 0; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}
	
	/**
	 * 生成密钥
	 * @param appId
	 * @return   Map<String, String>  
	 * 				key
	 * 				secret
	 */
	public static Map<String, String> getSecretKey(String appId){
		Map<String, String> sercretKey = null;
		if(StringUtils.isNotBlank(appId)){
			sercretKey = new HashMap<String, String>();
			String time = new Date().getTime()+"";
	        String str=AddSHAUtil.SHA256(appId+":"+time);
	        sercretKey.put("key", str.substring(0, 16));
	        sercretKey.put("secret", str.substring(16));
		}
		return sercretKey;
	}
	
	public static void main(String[] args) {
        System.out.println(getSecretKey("123"));
    }
}