package com.core.teamwork.base.session;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

/** 
 * @ClassName: MySessionContext
 * @author cyl
 * 
 */ 
public class MySessionContext {
	private static MySessionContext instance = new MySessionContext();
	/** 
	 * @Fields mymap : 用来缓存一次请求中的session
	 */ 
	public ConcurrentHashMap<String,HttpSession> mymap;
	/** 
	 * @Fields REDIS_SESSION_KEY : Redis中保存一个session的key
	 */ 
	
	private MySessionContext() {
		mymap = new ConcurrentHashMap<String,HttpSession>();
	}

	public static MySessionContext getInstance() {
		return instance;
	}

	/**
	 * cyl
	 * @param session
	 */
	public void DelMapSession(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());//删除缓存的session
		}
	}
	
}