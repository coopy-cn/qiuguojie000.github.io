package com.core.teamwork.base.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class XorTest {
	
	public static void main(String[] args) throws UnsupportedEncodingException {  
        //String value = "肉、蔬菜和水果";  
        //String key = "蔬菜和水果";  
        String value = "http://blog.csdn.net/froole/archive/2009/05/13/4176111.aspx";  
        String key = "key";  
          
        // 加密前输出  
        print("加密前", value);  
        // 加密处理  
        byte[] byteEncodeArray = encode(value.getBytes(), key);  
        value = new String(byteEncodeArray);  
          
        // 加密后输出  
        print("加密后", value);  
          
        // URL转换处理  
        String encode = URLEncoder.encode(value, "UTF-8");  
        print("转换URL", encode);  
        String dencode = URLDecoder.decode(encode, "UTF-8");  
        print("从URL回复", dencode);  
        value = dencode;  
          
        // 解密  
        byte[] byteDecodeArray = decode(value.getBytes(), key);  
        value = new String(byteDecodeArray);  
          
        // 解密后输出  
        print("解密", value);  
    }  
      
    /** 
     * 加密处理 
     * @param src 
     * @param key 
     * @return 
     */  
    private static byte[] encode(byte[] src, String key) {  
        byte[] byteKeyArray = new byte[0];  
        byte[] byteEncArray = new byte[src.length];  
          
        // 转换加密钥匙的循环处理  
        while(byteKeyArray.length < src.length) {  
            byteKeyArray = (new String(byteKeyArray) + key).getBytes();  
        }  
          
        // 转换  
        for (int i = 0; i < src.length; i++) {  
            byteEncArray[i] = (byte)(src[i]^byteKeyArray[i]);  
        }  
        return byteEncArray;  
    }  
      
    /** 
     * 解密 
     * @param src 
     * @param key 
     * @return 
     */  
    private static byte[] decode(byte[] src, String key) {  
        return encode(src, key);  
    }  
      
    /** 
     * 转换成16进制文字 
     * @param value 
     * @return 
     */  
    private static String getDump16(byte[] value) {  
          
        StringBuffer buf = new StringBuffer();  
          
        for (int i = 0; i < value.length; i++) {  
            String hex = Integer.toHexString((int)value[i] & 255);  
              
            // 添补前4位  
            hex = "0000" + hex;  
            hex = hex.substring(hex.length() - 4, hex.length());  
              
            // 添加空白并且每10位变行(空白区切り、10桁ずつ改行)  
            buf.append(hex + (i % 10 == 9?System.getProperty("line.separator"):" "));  
        }  
        return buf.toString().trim();  
    }  
      
    private static void print(String title, String value) {  
        System.out.println("【 " + title + " 】");  
        System.out.println("-----------------------------");  
        System.out.println(value);  
        System.out.println(getDump16(value.getBytes()));  
        System.out.println();  
        System.out.println();  
    }  
}
