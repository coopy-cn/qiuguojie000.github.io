/**   
 * @Title: MD5Util.java
 * @Package cn.devstore.teamwork.devstore.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author yangwenguang
 * @date 2015-5-20 下午10:13:33 
 * @version V1.0
 */
package com.core.teamwork.base.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @ClassName: MD5Util
 * @Description: MD5加密的工具类
 * @author yangwenguang
 * @date 2015-5-20 下午10:13:33
 * 
 */
public class MD5 {
    
    public static MD5 md5=new MD5();
    private MD5(){
        
    }

    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) {//e26d653e0aac0d5f39f819e0f0c771ae
    	 Date date=new Date();
         date.toString();
        System.out.println(md5.GetMD5Code("admin{cn.iyizhan.teamwork}"));
//        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("aa", "1");
//        Long l = (Long)map.get("aa");
////        System.out.println(Long.parseLong((String) map.get("aa")));
//        System.out.println(l);
    }
}
