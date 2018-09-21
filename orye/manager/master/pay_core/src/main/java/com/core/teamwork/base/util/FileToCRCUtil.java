/**  
 * @Title: FileToCRCUtil.java
 * @Package com.ijm.core.util
 * @Description: TODO
 * @author pengzhihao
 * @date 2016-4-29
 */
package com.core.teamwork.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * <P>
 * ClassName: FileToCRCUtil
 * </P>
 * <P>
 * @Description: TODO
 * </P>
 * <P>
 * Company:
 * </P>
 * 
 * @author pengzhihao
 * @date 2016-4-29下午5:33:13
 */
public class FileToCRCUtil {

	/**
	 * @Description: 获取crc32
	 * @param @param filePath
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author pengzhihao
	 * @date 2016-4-29下午5:34:47
	 */
	public static String getCRC32(String filePath) {
		CRC32 crc32 = new CRC32();
		FileInputStream fileinputstream = null;
		CheckedInputStream checkedinputstream = null;
		String crc = null;
		try {
			fileinputstream = new FileInputStream(new File(filePath));
			byte[] buf = new byte[2048];
			checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
			while (checkedinputstream.read(buf) != -1) {
			}
			crc = Long.toHexString(crc32.getValue()).toLowerCase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileinputstream != null) {
				try {
					fileinputstream.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			if (checkedinputstream != null) {
				try {
					checkedinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return crc;
	}
	
	/**
	 * @Description: 获取crc32 Long
	 * @param @param filePath
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author pengzhihao
	 * @date 2016-5-14下午5:40:43
	 */
	public static Long getCRC32Long(String filePath) {
		CRC32 crc32 = new CRC32();
		FileInputStream fileinputstream = null;
		CheckedInputStream checkedinputstream = null;
		Long crc = null;
		try {
			fileinputstream = new FileInputStream(new File(filePath));
			checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
			byte[] buf = new byte[2048];
			while (checkedinputstream.read(buf) != -1) {
			}
			//crc = Long.toHexString(crc32.getValue()).toLowerCase();
			crc = crc32.getValue();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileinputstream != null) {
				try {
					fileinputstream.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			if (checkedinputstream != null) {
				try {
					checkedinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return crc;
	}
	
}
