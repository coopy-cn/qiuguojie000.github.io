package com.core.teamwork.base.util.push;

import com.core.teamwork.base.util.ReadPro;

/**
 * 推送消息类
 * @author Administrator
 *
 */
public class PushVO {
	
	private String iOS_path;
	private String iOS_password;
	private String android_secretKey;
	private String android_appKey;
	
	
	public void init(String appCode,Integer type){
		String sys = System.getProperties().getProperty("os.name");
		String path = null;
		String[] value = null;
		if(type == 1){ //IOS版本
			path = ReadPro.getValue("IOS_"+appCode);
			value = path.split("#");
			if(sys.contains("Win")){
				this.iOS_path = value[0];
			}else{
				this.iOS_path = value[1];
			}
			this.iOS_password = value[2];
		}else{	//安卓版本
			path = ReadPro.getValue("AND_"+appCode);
			value = path.split("#");
			this.android_secretKey = value[0];
			this.android_appKey = value[1];
		}
		
	}
	
	
	/**
	 * @return the iOS_path
	 */
	public String getiOS_path() {
		return iOS_path;
	}
	/**
	 * @param iOS_path the iOS_path to set
	 */
	public void setiOS_path(String iOS_path) {
		this.iOS_path = iOS_path;
	}
	/**
	 * @return the iOS_password
	 */
	public String getiOS_password() {
		return iOS_password;
	}
	/**
	 * @param iOS_password the iOS_password to set
	 */
	public void setiOS_password(String iOS_password) {
		this.iOS_password = iOS_password;
	}
	/**
	 * @return the android_secretKey
	 */
	public String getAndroid_secretKey() {
		return android_secretKey;
	}
	/**
	 * @param android_secretKey the android_secretKey to set
	 */
	public void setAndroid_secretKey(String android_secretKey) {
		this.android_secretKey = android_secretKey;
	}
	/**
	 * @return the android_appKey
	 */
	public String getAndroid_appKey() {
		return android_appKey;
	}
	/**
	 * @param android_appKey the android_appKey to set
	 */
	public void setAndroid_appKey(String android_appKey) {
		this.android_appKey = android_appKey;
	}
	
	

}
