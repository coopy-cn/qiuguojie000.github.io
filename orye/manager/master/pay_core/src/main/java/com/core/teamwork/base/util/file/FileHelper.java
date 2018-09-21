package com.core.teamwork.base.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {


    private static FileHelper fileHelper = new FileHelper();

    public static FileHelper getInstance() {
        return fileHelper;
    }

    // 通过 sPath.matches(matches) 方法的返回值判断是否正确
    // sPath 为路径字符串

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     * 
     * @param sPath
     *            要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     * 
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
        	flag = file.delete();
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * 
     * @param sPath
     *            被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @Description: 复制文件
     * @param @param fileFrom 源文件
     * @param @param fileTo 目标文件
     * @param @param fileName 文件名  + 后缀
     * @param @return   
     * @return String  
     * @throws
     * @author pengzhihao
     * @date 2016-7-4下午2:28:12
     */
    public static String copyFile(String fileFrom, String fileTo,String fileName) {
    	try {
			FileInputStream in = new FileInputStream(fileFrom);  
			
			File file = new File(fileTo);
			if (!file.exists()) {
				file.mkdirs();
			}
			
			String path = "";
			if (!fileTo.endsWith(File.separator)) {
				path = fileTo + File.separator + fileName;
	        }else{
	        	path = fileTo + fileName;
	        }
			
			FileOutputStream out = new FileOutputStream(path);  
			byte[] bt = new byte[1024];  
			int count;  
			while ((count = in.read(bt)) > 0) {  
			    out.write(bt, 0, count);  
			}  
			in.close();  
			out.close();  
			return path;  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} 
    }

    public static void main(String[] args) {

//        FileHelper hfc = new FileHelper();
//        String path = "E:\\CloudMusic\\约瑟翰庞麦郎 - 我的滑板鞋.mp3";
//        boolean result = hfc.DeleteFolder(path);
//        System.out.println(result);
    	System.out.println(Integer.MAX_VALUE+1);

    }
}
