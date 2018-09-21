package com.core.teamwork.fileupload;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import com.core.teamwork.base.util.ReadPro;

public class FastdfsClientConfig {
	
	public static final int DEFAULT_CONNECT_TIMEOUT = 5; // second
	public static final int DEFAULT_NETWORK_TIMEOUT = 30; // second
	
	private int connectTimeout = DEFAULT_CONNECT_TIMEOUT * 1000;
	private int networkTimeout = DEFAULT_NETWORK_TIMEOUT * 1000;
	private List<String> trackerAddrs = new ArrayList<String>();
	
	private String filePath;
//	private int trackerClientPoolMaxIdlePerKey = 
	
//	public FastdfsClientConfig() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	
	public FastdfsClientConfig() throws ConfigurationException {
		super();
//		String conf = FastdfsClientConfig.class.getClassLoader().getResource(configFile).getPath();
//		Configuration config = new PropertiesConfiguration(configFile);
		this.connectTimeout = ReadPro.getValue("connect_timeout", DEFAULT_CONNECT_TIMEOUT)*1000;
		this.networkTimeout = ReadPro.getValue("network_timeout", DEFAULT_NETWORK_TIMEOUT)*1000;
		List<Object> trackerServers = ReadPro.getValue("tracker_server",new ArrayList<Object>());
		for(Object trackerServer:trackerServers){
			trackerAddrs.add((String)trackerServer);
		}
		this.filePath = ReadPro.getValue("visit_path");
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getNetworkTimeout() {
		return networkTimeout;
	}

	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	public List<String> getTrackerAddrs() {
		return trackerAddrs;
	}

	public void setTrackerAddrs(List<String> trackerAddrs) {
		this.trackerAddrs = trackerAddrs;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public GenericKeyedObjectPoolConfig getTrackerClientPoolConfig(){
		GenericKeyedObjectPoolConfig poolConfig = new GenericKeyedObjectPoolConfig();
//		poolConfig.setMaxIdlePerKey(maxIdlePerKey);
//		poolConfig.setMaxTotal(maxTotal);
//		poolConfig.setMaxTotalPerKey(maxTotalPerKey);
//		poolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//		poolConfig.setma
		
		return poolConfig;
	}
	

	public GenericKeyedObjectPoolConfig getStorageClientPoolConfig(){
		GenericKeyedObjectPoolConfig poolConfig = new GenericKeyedObjectPoolConfig();
		return poolConfig;
	}

}
