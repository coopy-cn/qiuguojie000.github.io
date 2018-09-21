package com.orye.manger.log;

import java.io.Serializable;


public class SessionVO implements Serializable{
	
	// appID
	private String appId;
	private Long sid;
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
