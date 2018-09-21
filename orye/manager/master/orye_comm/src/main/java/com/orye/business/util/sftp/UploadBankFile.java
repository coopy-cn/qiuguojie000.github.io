package com.orye.business.util.sftp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.core.teamwork.base.util.ReadPro;
import com.core.teamwork.base.util.ftp.FtpUploadClient;

public class UploadBankFile {
	
	/**
	 * 创建txt写入，ftp上传服务器，sftp上传到银行服务器
	 * @param localFileName
	 * @param localPath
	 * @param sdSb
	 * @param pathStr
	 * @throws IOException
	 */
	public static void upload(String localFileName,String localPath,StringBuffer sdSb,String pathStr) throws IOException{
		File f = new File(localPath+localFileName);  
        if (f.exists()) {  
            System.out.print("文件存在");  
        } else {  
            System.out.print("文件不存在");  
            f.createNewFile();// 不存在则创建  
        }  
        
        BufferedWriter output = new BufferedWriter(new FileWriter(f),1);  
        output.write(sdSb.toString());  
        output.close();  
        
        //ftp上传到服务器
        FtpUploadClient ftp = new FtpUploadClient();
		String ftpDir = pathStr+File.separator+System.currentTimeMillis()+File.separator;
		//upGetReady 第一个参数是路径上传路径    第二个参数是文件路径
		Boolean flag = ftp.upGetReady(ftpDir, f.getPath());
        
		
		//sftp上传到银行服务器
		SFTPUtils sftp = new SFTPUtils(ReadPro.getValue("sftp.upload.address")
				,Integer.valueOf(ReadPro.getValue("sftp.upload.port")), ReadPro.getValue("sftp.upload.name")
				, ReadPro.getValue("sftp.upload.pwd"));  
    	sftp.uploadFile(ReadPro.getValue("sftp.upload.path.in"), localFileName, localPath, localFileName);
		
        
        if(null!=f && f.exists()){
        	f.delete();
        }
	}
}
