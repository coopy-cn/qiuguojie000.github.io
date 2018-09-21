package com.core.teamwork.base.util.insertid;
/**  
 * @Title: MakeOnlineApkUtil.java
 * @Package com.ijm.gc.util
 * @Description: TODO
 * @author pengzhihao
 * @date 2016-5-17
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;


/**
 * <P>
 * ClassName: MakeOnlineApkUtil
 * </P>
 * <P>
 * @Description: TODO
 * </P>
 * <P>
 * Company:
 * </P>
 * 
 * @author pengzhihao
 * @date 2016-5-17 9:53:41
 */
public class InsertIDTool {
	
	private boolean IsExist(String strPath)
	{
		File file = new File(strPath);
		if(file.exists())
		{
			return true;
		}
		
		return false;
	}
	
	private void deleteFile(String strPath)
	{
		File file = new File(strPath);
		if(file.exists())
		{
			file.delete();
		}
	}
	
	//复制文件
	private static boolean  copyFile(String  oldPath,  String  newPath) 
	 {    
	       try  {    
	//		           int  bytesum  =  0;    
	           int  byteread  =  0;    
	           File  oldfile  =  new  File(oldPath);    
	           if  (oldfile.exists())  {  //文件存在时    
	               InputStream  inStream  =  new  FileInputStream(oldPath);  //读入原文件   
		               FileOutputStream  fs  =  new  FileOutputStream(newPath);    
		               byte[]  buffer  =  new  byte[1444];    
	  
		               while  (  (byteread  =  inStream.read(buffer))  !=  -1)
		               {    
	 
		                   fs.write(buffer,  0,  byteread);    
		               }    
		               inStream.close(); 
		               fs.close();
		           }    
		       }    
		       catch  (Exception  e)  
		       {    
		           System.out.println("复制单个文件操作出错");
		           return false;
	       } 
	       
	       return true;
	 }
	
	private boolean SysCmdEx(String command)
	{			
		CommandLine cmdLine = CommandLine.parse(command);  
		DefaultExecutor executor = new DefaultExecutor();  
		 
		executor.setExitValue(0);
		int exitValue = 1;		  
		
		try {
			exitValue = executor.execute(cmdLine);
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
		if(0 == exitValue)
		{
			return true;
		}
		
		return false;
	}	
	
	private boolean baksmali(String apkFilePath, String dir_baksmali)
	{
		
		String command = "java -jar " + m_strToolsPath + "apktool.jar d -f " + apkFilePath
					+ " -o " + dir_baksmali;
					
		 return SysCmdEx(command);
	}
	
	private boolean smali(String dir_smali, String apk_temp){
	
		String command = "java -jar "+ m_strToolsPath +"apktool.jar b -f " + dir_smali
				+ " -o " + apk_temp;
				
		return SysCmdEx(command);
	}
	
	private boolean InSertGameID(String strPath, Map<String, String> mapMetadata) throws IOException
	{
		strPath = strPath.replace("\\", "/");		
		String strManPath = strPath + "/" +m_strManifestName;
		File ManFile = new File(strManPath);		
		
		if(!ManFile.exists())
		{
			System.out.println(strManPath + " err");
			return false;
		}		
		javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();  
        try  
        {         	  
        	javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();         	
        	InputStream in=new FileInputStream(strManPath);
        	org.w3c.dom.Document doc = db.parse(in);      
        	org.w3c.dom.Element rootElement= doc.getDocumentElement();
        	if(null == rootElement)
            {
            	System.out.println("not found manifest");
            	return false;
            }
        	
        	//找到包名
        	org.w3c.dom.NodeList manifestList = doc.getElementsByTagName("manifest");
            if(manifestList.getLength() < 1)
            {
            	System.out.println("not found manifest");
            	return false;
            }            
            org.w3c.dom.Element manifest = (org.w3c.dom.Element)manifestList.item(0);
            m_ApkPackageName = manifest.getAttribute("package");
        	
        	org.w3c.dom.NodeList applicationList = doc.getElementsByTagName("application");
            if(applicationList.getLength() < 1)
            {
            	System.out.println("not found application");
            	return false;
            }        
            //插入meta-data
            org.w3c.dom.Element application = (org.w3c.dom.Element)applicationList.item(0);
            
            org.w3c.dom.NodeList metaDataList = application.getElementsByTagName("meta-data");
            for(int i = 0; i < metaDataList.getLength(); i++)
            {
            	org.w3c.dom.Element metaEle = (org.w3c.dom.Element)metaDataList.item(i);
            	String strName = metaEle.getAttribute("android:name");
            	if(mapMetadata.containsKey(strName))
            	{
            		metaEle.setAttribute("android:value", mapMetadata.get(strName));
            		mapMetadata.remove(strName);
            	}
            }
            
            for(String strMetaName : mapMetadata.keySet())
            {
            	org.w3c.dom.Element MetadataElement = doc.createElement("meta-data");
            	MetadataElement.setAttribute("android:name", strMetaName);
            	String strMetaValue = mapMetadata.get(strMetaName);
            	MetadataElement.setAttribute("android:value", strMetaValue);
            	application.appendChild(MetadataElement);
            }         
            //保存
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            former.setOutputProperty(OutputKeys.INDENT, "yes");
            former.transform(new DOMSource(doc), new StreamResult(new File(strManPath)));
        }  
        catch (Exception e)  
        {  
        	System.out.println("parse "+strManPath+" fail");
        	return false;
        } 
        
        return true;        
	}

	private boolean CreateNewPEMFile(String strPem, String strPrivateFile, String strCertFile) throws IOException
	{
		File pemFile = new File(strPem);
		String strFileData = FileUtils.readFileToString(pemFile);		
	
		String strTempbuf = strFileData;
		
		int nposBegin = 0, nposMid = 0, nposEnd;
		nposBegin = strFileData.indexOf("-----BEGIN PRIVATE", 0);
		
		if(-1 != nposBegin)
		{
			nposMid = strTempbuf.indexOf("-----END PRIVATE", nposBegin);
			nposEnd = strTempbuf.indexOf("\r\n", nposMid);			
			if(-1 == nposEnd)
			{
				nposEnd = strTempbuf.indexOf("\n", nposMid);
				if(-1 == nposEnd)
				{
					nposEnd = strTempbuf.indexOf("\r", nposMid);
				}
			}
		}
		else
		{
			return false;
		}

		String strSubPrivatestr = strTempbuf.substring(nposBegin, nposEnd);

		nposBegin = 0;
		nposMid = 0;
		nposEnd = 0;
		nposBegin = strTempbuf.indexOf("-----BEGIN CERTIFICATE", 0);
		if(-1 != nposBegin)
		{
			nposMid = strTempbuf.indexOf("-----END CERTIFICATE", nposBegin);
			nposEnd = strTempbuf.indexOf("\r\n", nposMid);
			if(-1 == nposEnd)
			{
				nposEnd = strTempbuf.indexOf("\n", nposMid);
				if(-1 == nposEnd)
				{
					nposEnd = strTempbuf.indexOf("\r", nposMid);
				}
			}
		}
		else
		{
			return false;
		}

		String strSubCretstr = strTempbuf.substring(nposBegin, nposEnd);

		File privFile = new File(strPrivateFile);
		privFile.createNewFile();	
		FileUtils.write(privFile, strSubPrivatestr);	
		if(!privFile.exists())
		{
			return false;
		}

		File cretFile = new File(strCertFile);
		cretFile.createNewFile();	
		FileUtils.write(cretFile, strSubCretstr);	
		if(!cretFile.exists())
		{
			return false;
		}	
		
		return true;
	}
	
	//删除zip包中指定的文件
	private  boolean rmPath(String strAaptPath,String zipPath, String path) throws ExecuteException, IOException
	{		
		String command = strAaptPath + " r" + " " + zipPath + path;
		return SysCmdEx(command);
	}
	
	//获取一个apk中的签名文件	
	private String GetSignFileByZip(String zipPath)
	{
		String strFiles = new String();
		try 
		{
			FileInputStream in = new FileInputStream(zipPath);
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry entry;
			while ((entry = zin.getNextEntry()) != null)
			{
				String strFilePath = entry.getName();
				String strSige = "META-INF";
				if(strFilePath.length() > 8)					
				{
					String strPos = strFilePath.substring(0, 8);
					if(0 == strPos.compareTo(strSige))
					{
						strFilePath = strFilePath.replace(File.separator, "/");
						strFiles += " "+ strFilePath;
						zin.closeEntry();
					}
				}				
			}
			zin.close();
			in.close();
		} 
		catch (IOException e)
		{
		}
		
		return strFiles;
	}
	
	private boolean deleteMeta_Info(String strAaptPath, String strApkPath) throws Exception
	{
		String SignFile = GetSignFileByZip(strApkPath);
		if(SignFile.length() > 0)
		{			
			if(!rmPath(strAaptPath, strApkPath, SignFile))
			{
				System.out.println("delete "+ SignFile+ " by " + strAaptPath + " fail");
			}			
		}
		else
		{
			System.out.println("not found  META-INF");
		}
		
//		File apkFile = new File(strApkPath);
//		if(!apkFile.exists())
//		{
//			System.out.println(strApkPath + " no exists");
//			return null;
//		}
//		strApkPath = strApkPath.replace("\\", "/");
//		
//		String strUnzipPath = strApkPath.substring(0,strApkPath.lastIndexOf("."));
//		
//		if(!ZipUtil.unzip(strApkPath, strUnzipPath, false))
//		{
//			System.out.println("unzip  " + strApkPath + "fail");
//		}		
//		
//		File InfFile = new File(strUnzipPath+"/META-INF");
//		FileUtils.deleteDirectory(InfFile);			
//		
//		int iNamePos = strApkPath.lastIndexOf("/");
//		String strApkName = strApkPath.substring(iNamePos +1);
//		String strFileExe = strApkName.substring(strApkName.lastIndexOf('.'));
//		strApkName = strApkName.substring(0, strApkName.lastIndexOf('.'));
//		strApkName += "tmp"+strFileExe;
//		strApkPath = strApkPath.substring(0, iNamePos);
//		
//		if(ZipUtil.zip(strUnzipPath, strApkPath, strApkName))
//		{
//			File unFile = new File(strUnzipPath);
//			FileUtils.deleteDirectory(unFile);
//			return strApkPath+"/"+strApkName;
//		}
//		else
//		{
//			System.out.println("zip  " + strUnzipPath + "fail");
//		}
		
		return true;
	}
	
	
	/**初始化外部调用
	 * @param strToolsPath 工具路径
	 * @param strConfigPath	配置文件路径
	 * @return 路径存在返回ture否则返回false
	 */
	public boolean init(String strToolsPath)
	{
		File fileToolsPath = new File(strToolsPath);
		//File fileConfigPath = new File(strConfigPath);
		if(!fileToolsPath.exists())
		{
			return false;
		}
		
		m_strToolsPath = strToolsPath.replace("\\", "/") + "/";	
	//	m_strConfigPath = strConfigPath.replace("\\", "/") + "/";
		return true;
	}
	
	/**在apk中插入指定的ID和值
	 * @param strApkPath apk路径
	 * @param mapMetadata 要插入的ID数据
	 * @return 返回插入ID后的apk 未签名
	 * @throws IOException
	 */
	public String InsertID(String strApkPath, Map<String, String> mapMetadata) throws IOException
	{	
		File fileApkPath = new File(strApkPath);
		if(!fileApkPath.exists())
		{
			return null;
		}	
		
		strApkPath = strApkPath.replace("\\", "/");			
		String strApkUnZipDir = strApkPath.substring(0, strApkPath.lastIndexOf("."));		
		//反编译
		if(!baksmali(strApkPath, strApkUnZipDir))
		{
			File unFile = new File(strApkUnZipDir);
			FileUtils.deleteDirectory(unFile);	
			System.out.println("baksmali fail :"+strApkPath);
			return null;
		}
		
		//插入ID
		if(!InSertGameID(strApkUnZipDir, mapMetadata))
		{
			File unFile = new File(strApkUnZipDir);
			FileUtils.deleteDirectory(unFile);	
			System.out.println("InSertGameID fail :"+strApkUnZipDir);
			return null;
		}
		
		//添加diayou.info文件
		if(null != m_ApkPackageName)
		{
			String strFilePath = strApkUnZipDir+"/assets/diayou.info";
			File InfoFile = new File(strFilePath);
			String strFileData = "packagename:";
			strFileData += m_ApkPackageName;
			FileUtils.writeStringToFile(InfoFile, strFileData);
		}
		else
		{
			File unFile = new File(strApkUnZipDir);
			FileUtils.deleteDirectory(unFile);
			System.out.println("not found  packagename!");
			return null;
		}
		
		//回编译
		String strMali = strApkUnZipDir + "_unsigned.apk";
		if(!smali(strApkUnZipDir, strMali))
		{
			File unFile = new File(strApkUnZipDir);
			FileUtils.deleteDirectory(unFile);
			System.out.println("smali " + strApkUnZipDir + "fail");
			return null;
		}	
					
		File unFile = new File(strApkUnZipDir);
		FileUtils.deleteDirectory(unFile);	
		
		return strMali;
	}

	public boolean InsertPic(String strApkPath, String strPicPath) throws Exception
	{
		File apkFile = new File(strApkPath);
		if(!apkFile.exists())
		{
			System.out.println(strApkPath + " no exists");
			return false;
		}
		strApkPath = strApkPath.replace("\\", "/");
		
		String strUnzipPath = strApkPath.substring(0,strApkPath.lastIndexOf("."));
		
		if(!ZipUtil.unzip(strApkPath, strUnzipPath, false))
		{
			System.out.println("unzip  " + strApkPath + "fail");
		}
		
		String strDstFilePath = strUnzipPath + "/assets/game_launcher.png";
		copyFile(strPicPath, strDstFilePath);
		
		File InfFile = new File(strUnzipPath+"/META-INF");
		FileUtils.deleteDirectory(InfFile);		
		
		apkFile.delete();
		
		int iNamePos = strApkPath.lastIndexOf("/");
		String strApkName = strApkPath.substring(iNamePos +1);
		strApkPath = strApkPath.substring(0, iNamePos);
		
		if(ZipUtil.zip(strUnzipPath, strApkPath, strApkName))
		{
			File unFile = new File(strUnzipPath);
			FileUtils.deleteDirectory(unFile);
			return true;
		}
		else
		{
			System.out.println("zip  " + strUnzipPath + "fail");
		}
		
		
		return false;
	}
	
	//生成KeyStore
	public boolean GenerateKeyStore(KeystoreInfo keystoreinfo) throws IOException
	{	
		//keystoreinfo.keystore = keystoreinfo.keystore.replace("\\", "/");
		//生成keystore
		String strKeystoreCmd = "keytool -genkey -alias "+ keystoreinfo.alias +
				" -keypass "+ keystoreinfo.keypass + " -keyalg "+ keystoreinfo.keyalg + 
				" -keysize " + keystoreinfo.keysize + " -validity " + keystoreinfo.validity + 
				" -keystore " + keystoreinfo.keystore + " -storepass " + keystoreinfo.storepass + 
				" -dname \"CN=(" + keystoreinfo.CN + "),OU=("+ keystoreinfo.OU + "),O=(" + keystoreinfo.O +
				"),L=(" + keystoreinfo.L + "),ST=(" + keystoreinfo.ST + "),C=(" + keystoreinfo.C+ ")\"";
		
		SysCmdEx(strKeystoreCmd);
		if(!IsExist(keystoreinfo.keystore))
		{
			System.out.println("Generate keystroe fail :" + strKeystoreCmd);
			return false;
		}		
		return true;
	}
	
	
	/**
	 * @param opensslPath openssl执行文件路径
	 * @keystoreinfo 生成keystore的一些信息 
	 * @strPk8Path pk8路径
	 * @strPemPath pem路径
	 * @return	成功返回true 失败返回false
	 * @throws IOException 
	 */
	public boolean GeneratePk8andPem(String opensslPath, KeystoreInfo keystoreinfo, String strPk8Path, String strPemPath) throws IOException
	{	
		//生成keystore
//		String strKeystoreCmd = m_strToolsPath + "keytool -genkey -alias "+ keystoreinfo.alias +
//				" -keypass "+ keystoreinfo.keypass + " -keyalg "+ keystoreinfo.keyalg + 
//				" -keysize " + keystoreinfo.keysize + " -validity " + keystoreinfo.validity + 
//				" -keystore " + keystoreinfo.keystore + " -storepass " + keystoreinfo.storepass + 
//				" -dname \"CN=(" + keystoreinfo.CN + "), OU=("+ keystoreinfo.OU + "), O=(" + keystoreinfo.O +
//				"), L=(" + keystoreinfo.L + "), ST=(" + keystoreinfo.ST + "), C=(" + keystoreinfo.C+ ")\"";
//		
//		SysCmdEx(strKeystoreCmd);
		if(!IsExist(keystoreinfo.keystore))
		{
			System.out.println("no keystroe");
			return false;
		}
		
		String strTmPath = strPk8Path.replace("\\", "/");
		strTmPath = strTmPath.substring(0, strTmPath.lastIndexOf("/"));
		
		String strTmpp12 = strTmPath + "/tmp.p12";		
		// 第一步：生成pkcs12格式文件 keytool -importkeystore -srckeystore TestPacket\zs.keystore -destkeystore tmp.p12 -srcstoretype JKS -deststoretype PKCS12
		String cmdline = "keytool  -importkeystore -srckeystore " + keystoreinfo.keystore +
				" -srcalias " + keystoreinfo.alias + " -destkeystore " + strTmpp12+ " -srcstoretype JKS -deststoretype PKCS12 -srcstorepass " + keystoreinfo.storepass +
				" -srckeypass "+ keystoreinfo.keypass +" -deststorepass " +keystoreinfo.keypass+ " -noprompt ";		
		SysCmdEx(cmdline);
		if(!IsExist(strTmpp12))
		{
			System.out.println("Generate strTmpp12 fail :" + cmdline);
			return false;
		}
		
		// 第二步：将PKCS12 dump成pem 
		String strPem = strTmPath +"/tmp.rsa.pem";			
		String cmdline2 = opensslPath + " pkcs12 -in "+ strTmpp12 +" -nodes -out "+  strPem+" -passin pass:" + keystoreinfo.keypass;
		SysCmdEx(cmdline2);
		deleteFile(strTmpp12);
		
		if(!IsExist(strPem))
		{
			System.out.println("Generate Pem fail :" + cmdline2);
			return false;
		}
		
		// 第三步：创建private.rsa.pem 以及 cert.x509.pem文件		
		String strPriv = strTmPath + "/private.rsa.pem";		
		if(!CreateNewPEMFile(strPem, strPriv, strPemPath))			
		{
			deleteFile(strPem);
			System.out.println("Generate Priv fail");
			return false;
		}
		if(!IsExist(strPemPath))
		{
			deleteFile(strPem);
			return false;
		}

		deleteFile(strPem);
		// 第四步：生成pk8格式的私钥openssl pkcs8 -topk8 -outform DER -in private.rsa.pem -inform PEM -out private.pk8 -nocrypt
		String cmdline3 = opensslPath + " pkcs8 -topk8 -outform DER -in " + strPriv + " -inform PEM -out " + strPk8Path + " -nocrypt ";
		SysCmdEx(cmdline3);
		deleteFile(strPriv);
		if(!IsExist(strPk8Path))
		{
			System.out.println("Generate Pk8 fail :" + cmdline3);
			return false;
		}
		
		return true;
	}
	
	
	/**给apk签名
	 * @param strPemPath pem文件路径 
	 * @param strPk8Path pk8文件路径
	 * @param apk_temp 需要签名的apk文件路径
	 * @param apk_last	签名之后的apk文件路径
	 */
	public boolean signature(String strPemPath, String strPk8Path, String apk_temp, String apk_last)
	{	
		try 
		{
			String strAaptPath = m_strToolsPath + "aapt";
			deleteMeta_Info(strAaptPath, apk_temp);		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("dele meta_info fail!");
			return false;
		}
		
		if(null == apk_temp)
		{
			System.out.println("dele meta_info fail!");
			return false;
		}
		
		String command = "java -jar " + m_strToolsPath + "signapk.jar "
				+ strPemPath + " " + strPk8Path +" " + apk_temp + " " + apk_last;
		
		if(!SysCmdEx(command))
		{
			System.out.println("sign "+ apk_temp +" fail!");
			return false;
		}
		
		deleteFile(apk_temp);
		return true;				
	}
	
	//apk压缩
	public boolean CompressApk(String strApkPath)
	{
		String command = m_strToolsPath +"CompressApk " + strApkPath;	
		//System.out.println(command);
		return SysCmdEx(command);	
	}
	
	private static String m_strManifestName="AndroidManifest.xml";
	private String m_strToolsPath = "tools/";
	
	private String m_ApkPackageName = null;
}
