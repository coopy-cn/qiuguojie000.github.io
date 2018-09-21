package com.core.teamwork.base.util.encrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip解压缩
 * 
 * @author smithJiang
 * 
 */
public class GZipTool {
	public static final int BUFFER = 1024;

	/**
	 * 压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 压缩
		GZIPOutputStream gos = new GZIPOutputStream(baos);
		gos.write(data, 0, data.length);
		gos.finish();
		byte[] output = baos.toByteArray();
		baos.flush();
		baos.close();
		return output;
	}

	/**
	 * 数据解压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 解压缩
		decompress(bais, baos);
		data = baos.toByteArray();
		baos.flush();
		baos.close();
		bais.close();
		return data;
	}

	/**
	 * 数据解压缩
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void decompress(InputStream is, OutputStream os)
			throws Exception {
		GZIPInputStream gis = new GZIPInputStream(is);
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}
		gis.close();
	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @param inStream
	 *            输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;

		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		outStream.flush();
		outStream.close();
		return outStream.toByteArray();
	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @param inStream
	 *            输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream, int posOffset,
			int byteCount) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int lastCount = byteCount % 1024;// 最后一次读取的大小
		try {
			int len = 0;
			// Long countLong = inStream.skip(posOffset);
			skipBytesFromStream(inStream, posOffset);
			int count = 0;
			while ((len = inStream.read(buffer, 0,
					byteCount - count < 1024 ? lastCount : 1024)) != -1) {
				count += len;
				if (count == byteCount) {
					outStream.write(buffer, 0, len);
					break;
				}
				outStream.write(buffer, 0, len);
			}
			inStream.close();
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outStream.toByteArray();
	}

	public static String readAndDecompress(InputStream inStream)
			throws Exception {
		String resultString = null;
		byte[] dataByte = readInputStream(inStream);
		dataByte = decompress(dataByte);
		resultString = new String(dataByte);
		return resultString;
	}

	/** 重写了Inpustream 中的skip(long n) 方法，将数据流中起始的n 个字节跳过 */
	private static long skipBytesFromStream(InputStream inputStream, long n) {
		long remaining = n;
		// SKIP_BUFFER_SIZE is used to determine the size of skipBuffer
		int SKIP_BUFFER_SIZE = 2048;
		// skipBuffer is initialized in skip(long), if needed.
		byte[] skipBuffer = null;
		int nr = 0;
		if (skipBuffer == null) {
			skipBuffer = new byte[SKIP_BUFFER_SIZE];
		}
		byte[] localSkipBuffer = skipBuffer;
		if (n <= 0) {
			return 0;
		}
		while (remaining > 0) {
			try {
				nr = inputStream.read(localSkipBuffer, 0,
						(int) Math.min(SKIP_BUFFER_SIZE, remaining));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nr < 0) {
				break;
			}
			remaining -= nr;
		}
		return n - remaining;
	}
}
