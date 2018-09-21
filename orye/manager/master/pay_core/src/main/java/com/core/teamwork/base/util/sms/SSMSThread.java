package com.core.teamwork.base.util.sms;

import java.io.InputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

public class SSMSThread implements Runnable {

	Logger logger = Logger.getLogger(this.getClass());

	private String phone_number;

	private String code;

	public SSMSThread(String phone_number, String code) {
		this.phone_number = phone_number;
		this.code = code;
	}
	
	static InputStream is=SSMSThread.class.getClassLoader().getResourceAsStream("ssms_config.properties");

	static PropertiesConfiguration propConfig;

	static Configuration multiConfig;

	private static void reloadPropFile() {
		if (null == propConfig) {
			try {
				propConfig = new PropertiesConfiguration();
				propConfig.load(is);
				FileChangedReloadingStrategy change = new FileChangedReloadingStrategy();
				change.setRefreshDelay(60 * 1000);// 检测文件是否改变的时长
				propConfig.setReloadingStrategy(change);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// @Override
	// public void run() {
	// SSMSUtil ssmsUtil = new SSMSUtil();
	// ssmsUtil.sendMessage(phone_number, code);
	// }
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		reloadPropFile();
		HttpSender ssmsUtil = new HttpSender();
		String url = propConfig.getString("url").trim();
		String name = propConfig.getString("account").trim();
		String password = propConfig.getString("password").trim();
		String content = String.format(propConfig.getString("content").trim(), code);
		try {
			ssmsUtil.batchSend(url, name, password, phone_number, content,true, null, null);
		} catch (Exception e) {
			logger.error("发送手机验证码失败", e);
		}
	}
}
