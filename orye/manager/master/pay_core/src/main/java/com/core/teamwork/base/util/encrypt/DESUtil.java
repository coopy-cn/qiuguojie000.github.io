package com.core.teamwork.base.util.encrypt;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.net.util.Base64;

/**
 * @author cyl DES与Base64混合的加密解密类
 */
public class DESUtil {
	private final static String DES = "DES";

	private final static String KEY = "cyl!@#$%";

	public static void main(String[] args) throws Exception {
		String data = "203";
		System.err.println(encrypt(data));
		System.err.println(decrypt("EM1SDR2XHic"));

	}

//	public static String safeUrlBase64Encode(byte[] data) {
//		String encodeBase64 = Base64.encodeBase64String(data);
//		String safeBase64Str = encodeBase64.replace('+', '-');
//		safeBase64Str = safeBase64Str.replace('/', '_');
//		safeBase64Str = safeBase64Str.replaceAll("=", "");
//		return safeBase64Str;
//	}

//	public static byte[] safeUrlBase64Decode(final String safeBase64Str) {
//		String base64Str = safeBase64Str.replace('-', '+');
//		base64Str = base64Str.replace('_', '/');
//		int mod4 = base64Str.length() % 4;
//		if (mod4 > 0) {
//			base64Str = base64Str + "====".substring(mod4);
//		}
//		return Base64.decodeBase64(base64Str);
//	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
//		byte[] bt = encrypt(data.getBytes(), key.getBytes());
//		String strs = safeUrlBase64Encode(bt);
//		return strs.trim();
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = Base64.encodeBase64String(bt);
		return strs.trim();
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
//		byte[] bt = encrypt(data.getBytes(), KEY.getBytes());
//		String strs = safeUrlBase64Encode(bt);
//		return strs.trim();
		byte[] bt = encrypt(data.getBytes(), KEY.getBytes());
		String strs = Base64.encodeBase64String(bt);
		return strs.trim();
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException,
			Exception {
		if (data == null) {
			return null;
		} else {
			byte[] buf = Base64.decodeBase64(data);
			byte[] bt = decrypt(buf, key.getBytes());
			return new String(bt);
		}
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data) throws IOException, Exception {
		if (data == null) {
			return null;
		} else {
			byte[] buf = Base64.decodeBase64(data);
			byte[] bt = decrypt(buf, KEY.getBytes());
			return new String(bt);
		}
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}
