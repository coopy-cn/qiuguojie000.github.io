package com.core.teamwork.base.util.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author cyl
 *获取文件MD5值
 */
public class FileMd5 {
	
	private static final Logger logger = Logger.getLogger(FileMd5.class);

    protected static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("MD5FileUtil messagedigest初始化失败", e);
		}
	}
    
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    
    public static String getMd5ByFile(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
				file.length());
		messagedigest.update(byteBuffer);
		return bufferToHex(messagedigest.digest());
	}
    
    private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
    
    /**
     * @Description: 取得MD5加密字符串。
     * @param @param str 加密前的字符串
     * @param @return   
     * @return String  
     * @throws
     * @author pengzhihao
     * @date 2016-7-10下午5:20:37
     */
    public static String getMD5(String str) {
        String digest = "";
        try {
        	if(StringUtils.isNotBlank(str)){
        		str = str.trim();
	            MessageDigest currentAlgorithm = MessageDigest.getInstance("md5");
	            currentAlgorithm.reset();
	            byte[] mess = str.getBytes();
	            byte[] hash = currentAlgorithm.digest(mess);
	            for (int i = 0; i < hash.length; i++) {
	                int v = hash[i];
	                if (v < 0) {
	                    v = 256 + v;
	                }
	                if (v < 16) {
	                    digest += "0";
	                }
	                digest += Integer.toString(v, 16).toUpperCase() + "";
	            }
        	}
        } catch (Exception ex) {
            digest = str;
        }

        return digest;
    }

    public static void main(String[] args) throws IOException {

        String path = "F:\\ff80808156a0f0160156a1ab96dc0002.apk";
        // String v = getMd5ByFile(new File(path));
        // System.out.println("MD5:" + v.toUpperCase());
       /* FileInputStream fis = new FileInputStream(path);
        System.out.println(fis.available());
        String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        IOUtils.closeQuietly(fis);*/
        String md5 = getMd5ByFile(new File(path));
        System.out.println("MD5:" + md5);

        // System.out.println("MD5:"+DigestUtils.md5Hex("WANGQIUYUN"));
    }

}
