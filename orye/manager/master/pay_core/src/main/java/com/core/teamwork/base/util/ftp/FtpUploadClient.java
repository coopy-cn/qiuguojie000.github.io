/**  
 * @Title: FtpUploadCli.java
 * @Package com.ijm.gc.util.ftp
 * @Description: TODO
 * @author pengzhihao
 * @date 2016-7-16
 */
package com.core.teamwork.base.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.core.teamwork.base.util.ReadPro;

/**
 * <P>
 * ClassName: FtpUploadCli
 * </P>
 * <P>
 * @Description: ftp上传客户端
 * </P>
 * <P>
 * Company:
 * </P>
 * 
 * @author pengzhihao
 * @date 2016-7-16下午4:28:57
 */
public class FtpUploadClient {
	private static Logger logger = Logger.getLogger(FtpUploadClient.class);
	/**
	 * 默认FTP客户端
	 */
	public static final int TYPE_DEFUALT = 1;
	/**
	 * 静态资源FTP客户端
	 */
	public static final int TYPE_STATIC = 2;

	// 上传ftp验证参数
	private String address = ReadPro.getValue("ftp.upload.address");
	private int port = Integer.parseInt(ReadPro.getValue("ftp.upload.port"));
	private String name = ReadPro.getValue("ftp.upload.name");
	private String pwd = ReadPro.getValue("ftp.upload.pwd");
	private String ftpPath = ReadPro.getValue("ftp.upload.path");

	// 上传ftp验证参数
	private static final String server = ReadPro.getValue("ftp.visit.path");

	public FtpUploadClient() {

	}

	public FtpUploadClient(String address, int port, String name, String pwd,
			String ftpPath) {
		this.address = address;
		this.port = port;
		this.name = name;
		this.pwd = pwd;
		this.ftpPath = ftpPath;
	}

	public static FtpUploadClient getInstance() {
		return getInstance(TYPE_DEFUALT);
	}

	public static FtpUploadClient getInstance(int type) {
		if (type == TYPE_STATIC) {
			String address = ReadPro.getValue("static.ftp.upload.address");
			int port = Integer.parseInt(ReadPro
					.getValue("static.ftp.upload.port"));
			String name = ReadPro.getValue("static.ftp.upload.name");
			String pwd = ReadPro.getValue("static.ftp.upload.pwd");
			String ftpPath = ReadPro.getValue("static.ftp.upload.path");
			return new FtpUploadClient(address, port, name, pwd, ftpPath);
		} else {
			return new FtpUploadClient();
		}
	}

	/**
	 * @Description: 上传ftp路径
	 * @param @param fileDir ftp文件目录 如：/data/xxx/yyyyMMdd/
	 * @param @param filePath 上传文件
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author pengzhihao
	 * @date 2016-7-17下午1:59:27
	 */
	public boolean upGetReady(String fileDir, String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			// 源文件不存在
			logger.error("源文件不存在");
			return false;
		}

		String fileFtpPath = ftpPath + fileDir;
		// 上传ftp返回状态
		boolean statu = false;

		filePath = filePath.replace("\\", "/");
		fileFtpPath = fileFtpPath.replace("\\", "/");
		// 上传ftp文件名称
		String filename = filePath.substring(filePath.lastIndexOf("/") + 1,filePath.length());
		try {
			// 上传ftp文件流
			FileInputStream input = new FileInputStream(file);
			// 处理上传
			logger.info("ftp上传目录：" + fileFtpPath);
			logger.info("开始上传ftp...");
			statu = FtpUtils.uploadFile(fileFtpPath, filename, input, address,
					port, name, pwd);
			logger.info("上传ftp结束..." + statu);
		} catch (FileNotFoundException e) {
			logger.error("上传ftp异常：", e);
			statu = false;
		}

		return statu;
	}

	public String uploadFile(String filename, InputStream input) {
		String filePath = null;
		boolean statu = FtpUtils.uploadFile(ftpPath, filename, input, address,
				port, name, pwd);
		if (statu) {
			filePath = server + ftpPath + filename;
		}
		return filePath;
	}

	public String uploadFile(String filename, File file) {
		try {
			if (file.exists()) {
				InputStream in = new FileInputStream(file);
				return uploadFile(filename, in);
			} else {
				return null;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
