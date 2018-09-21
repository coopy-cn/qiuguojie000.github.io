package com.core.teamwork.base.util.ftp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtils {


	/** 本地字符编码 */
	private static String LOCAL_CHARSET = "GBK";
	 
	// FTP协议里面，规定文件名编码为iso-8859-1
	private static String SERVER_CHARSET = "ISO-8859-1";
	
	/**
	 * @Description: 通过流上传本地文件到Ftp服务器
	 * @param @param path 需要保存到Ftp服务器的路径信息 ps：/test/apk/
	 * @param @param filename  文件名称
	 * @param @param input 需要上传的文件流
	 * @param @param address ftp地址
	 * @param @param port 端口号
	 * @param @param name 登录名
	 * @param @param pwd 密码
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author pengzhihao
	 * @date 2015-12-14上午10:32:42
	 */
	public static boolean uploadFile(String path, String filename,
			InputStream input,String address,int port,String name,String pwd) {
		// 过滤掉的文件类型
        String[] errorType = { ".exe", ".com", ".cgi", ".asp", ".php", ".jsp", ".dll", ".bat" };
        for (int temp = 0; temp < errorType.length; temp++) {
            if (filename.endsWith(errorType[temp])) {
                filename = filename + ".danger";
            }
        }
        
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(address, port);
			
			if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				if (ftp.login(name, pwd)) {
					if (FTPReply.isPositiveCompletion(ftp.sendCommand(
					"OPTS UTF8", "ON"))) {// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
					LOCAL_CHARSET = "UTF-8";
					}
				}
			}else{
				ftp.disconnect();
				return success;
			}
			ftp.setControlEncoding(LOCAL_CHARSET);
			ftp.setBufferSize(1024 * 1024);
			splitDir(ftp,path);
			//被动
			ftp.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			//登陆后设置文件类型为二进制否则可能导致乱码文件无法打开 
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			//上传文件时，文件名称需要做编码转换
			filename = new String(filename.getBytes(LOCAL_CHARSET),SERVER_CHARSET);
			ftp.storeFile(filename, input);
			//input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	/**
	 * @Description: ftp上传判断文件夹是否存在并创建文件
	 * true没有这个文件夹，false是有
	 * @param @param ftp
	 * @param @param path   
	 * @return void  
	 * @throws
	 * @author pengzhihao
	 * @date 2015-12-14上午10:33:42
	 */
	public static void splitDir(FTPClient ftp ,String path) {
		String[] paths = path.split("/");
		String parent = "";
		for (int i = 0; i < paths.length; i++) {
			if (paths[i].equals(""))
				continue;
			parent += "/" + paths[i];
			try {
				ftp.makeDirectory(parent);
				ftp.cwd(parent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Description: 获取ftp连接
	 * @param @param serverAddr
	 * @param @param port
	 * @param @param account
	 * @param @param password
	 * @param @return   
	 * @return FTPClient  
	 * @throws
	 * @author pengzhihao
	 * @date 2015-12-14上午10:34:40
	 */
	public static FTPClient getFTPClient(String serverAddr, int port,
			String account, String password) {
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(serverAddr, port);
			ftp.login(account, password);
			ftp.setBufferSize(1024 * 1024);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return ftp;
			}
			ftp.enterLocalPassiveMode();
			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (IOException e) {
		}
		return ftp;
	}

	/**
	 * @Description: 断开ftp连接
	 * @param @param ftp   
	 * @return void  
	 * @throws
	 * @author pengzhihao
	 * @date 2015-12-14上午10:34:56
	 */
	public static void disconnect(FTPClient ftp) {
		if (ftp != null && ftp.isConnected()) {
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException ioe) {
			}
		}
	}
}
