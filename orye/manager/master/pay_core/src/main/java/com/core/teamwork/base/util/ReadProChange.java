package com.core.teamwork.base.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;

/**
 * @author cyl
 * @date 2016年6月1日 11:38:35
 */
public class ReadProChange {
	static String path = ReadProChange.class.getClassLoader().getResource("config.properties").getPath();
	// static InputStream
	// is=ReadPro.class.getClassLoader().getResourceAsStream("config.properties");

	static PropertiesConfiguration propConfig;

	static Configuration multiConfig;
	
	public static Integer timeChange;

	private ReadProChange() {
	}

	private static void reloadPropFile() {
		if (null == propConfig) {
			try {
//				propConfig = new PropertiesConfiguration();
//				propConfig.load(is);
				propConfig = new PropertiesConfiguration(new File(path));
				FileChangedReloadingStrategy change = new FileChangedReloadingStrategy();
				change.setRefreshDelay(timeChange == null ?60 * 1000:timeChange);// 检测文件是否改变的时长
				propConfig.setReloadingStrategy(change);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getValue(String key, T defaultValue) {
		reloadPropFile();
		T value = null;
		if (StringUtils.isNotBlank(key)) {
			String keyValue = propConfig.getString(key);
			if (StringUtils.isNotBlank(keyValue)) {
				if (null != defaultValue) {
					if (defaultValue instanceof List) {
						value = (T) propConfig.getList(key,
								(List<?>) defaultValue);
					} else if (defaultValue instanceof String[]) {
						value = (T) propConfig.getStringArray(key);
					} else if (defaultValue instanceof Integer) {
						value = (T) propConfig.getInteger(key,
								(Integer) defaultValue);
					} else if (defaultValue instanceof String) {
						value = (T) propConfig.getString(key,
								(String) defaultValue);
					} else if (defaultValue instanceof Long) {
						value = (T) propConfig
								.getLong(key, (Long) defaultValue);
					} else if (defaultValue instanceof Float) {
						value = (T) propConfig.getFloat(key,
								(Float) defaultValue);
					} else if (defaultValue instanceof Boolean) {
						value = (T) propConfig.getBoolean(key,
								(Boolean) defaultValue);
					}
				} else {
					value = (T) keyValue;
				}

			} else {
				value = defaultValue;
			}
		}

		return value;
	}

	public static String getValue(String key) {
		reloadPropFile();
		String s = propConfig.getString(key);
		return StringUtils.isNotBlank(s) ? s : null;
	}

	public static void setTimeChange(Integer timeChange) {
		ReadProChange.timeChange = timeChange;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(ReadProChange.getValue("connect_timeout"));
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
