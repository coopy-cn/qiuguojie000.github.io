package com.core.teamwork.base.util.cdn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesRequest;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.core.teamwork.base.util.ReadPro;

public class AliCDNUtil {
	private static Logger logger = Logger.getLogger(AliCDNUtil.class);
	
	private static final String accessKeyId = ReadPro.getValue("aliCdnAccessKeyId","LTAId86gWGdkZM52");
	private static final String accessKeySecret = ReadPro.getValue("aliCdnAccessKeySecret","kE4r80myjcPRfpO4aC4RM26HYxSYt5");
	private static final String bucketName = ReadPro.getValue("aliCdnBucketName","dyt1");
private static final String rootDir = ReadPro.getValue("ftp.upload.path");
	
	private static IAcsClient iAcsClient;
	private static OSSClient ossClient;
	
	private AliCDNUtil(){}
	
	static{
		IClientProfile profile = DefaultProfile.getProfile("cn-shenzhen",accessKeyId,accessKeySecret);
		iAcsClient = new DefaultAcsClient(profile);
		ossClient = new OSSClient("http://oss-cn-shenzhen.aliyuncs.com",accessKeyId,accessKeySecret);
	}
	
	/**
	 * 刷新阿里云CDN缓存
	 * @return
	 */
	public static void refreshCache(final String url){
		try {
			logger.info("ready to refresh ALI CDN.");
			RefreshObjectCachesRequest request = new RefreshObjectCachesRequest();
			//File：文件 Directory：目录
			//request.setObjectType("File");
			//request.setAcceptFormat(FormatType.JSON);
			request.setObjectPath(url);
			HttpResponse response = iAcsClient.doAction(request);
			logger.info("refresh ALI CDN stats:"+response.getStatus()+"\tcontent:"+new String(response.getContent()));
		} catch (Exception e) {
			logger.error("refresh ALI CDN error",e);
		}
	}
	
	/**
	 * @Description: 阿里云CDN上传文件
	 * @param @param dir
	 * @param @param fileName
	 * @param @param pathName
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author pengzhihao
	 * @date 2016-12-21下午10:18:49
	 */
	public static boolean addFile(String dir,String filePath){
		try {
			filePath = filePath.replace("\\", "/");
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1,filePath.length());
			File file = new File(filePath);
			if(file.exists()){
				return addFile(dir,fileName,new FileInputStream(file));
			}
		} catch (Exception e) {
			logger.error("ALI CDN upload file error",e);
		}
		return false;
	}
	
	/**
	 * 阿里云CDN上传文件
	 * @param dir	CDN路径
	 * @param fileName	文件名
	 * @param pathName	待上传的本地文件路径
	 * @return
	 */
	public static boolean addFile(String dir,String fileName,String pathName){
		try {
			File file = new File(pathName);
			if(file.exists()){
				return addFile(dir,fileName,new FileInputStream(file));
			}
		} catch (Exception e) {
			logger.error("ALI CDN upload file error",e);
		}
		return false;
	}
	
	/**
	 * 阿里云CDN上传文件
	 * @param dir	CDN路径
	 * @param fileName	文件名
	 * @param inputStream	待上传的文件流
	 * @return
	 */
	public static boolean addFile(String dir,String fileName,InputStream inputStream){
		try {
			if(dir.indexOf("/")==0){
				dir = rootDir + dir;
			}else{
				dir = rootDir + File.separator + dir;
				dir = dir.replace("\\", "/");
			}
			dir = dir.substring(1);
			ossClient.putObject(bucketName,dir+fileName,inputStream);
			return true;
		} catch (Exception e) {
			logger.error("ALI CDN upload file error",e);
		}
		return false;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		//AliCDNUtil.addFile("data/test/","aaa.png",new FileInputStream("D:\\表情\\男孩100px\\棒棒哒.png"));
		AliCDNUtil.refreshCache("http://testosscdn.idianyou.cn/data/activity/img/20161219/2c8a8bdb5916d259015916d37fdc0001.png");
	}
}
