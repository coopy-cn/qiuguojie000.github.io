package com.orye.business.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class WebContextUtil {

	public static String getBasePath(HttpServletRequest request) {
		return getBasePath(request, true);
	}

	/**
	 * 获取项目路径
	 * @param request
	 * @param flag 为真带端口,为假不带端口
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request, boolean flag) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int port = request.getServerPort();
		String path = request.getContextPath();
		StringBuilder sb = new StringBuilder();
		if (flag) {
			sb.append(scheme).append("://").append(serverName).append(":")
					.append(port).append(path);
		} else {
			sb.append(scheme).append("://").append(serverName).append(path);
		}
		return sb.toString();
	}
	
	/**
	 * 获取接口地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestUrl(HttpServletRequest request){
		String requestUrl = request.getRequestURI();
		System.out.println(requestUrl);
		String rgex = "/?[a-zA-Z0-9]+/?[a-zA-Z0-9]+[.do]+";
		Pattern pattern = Pattern.compile(rgex);
		Matcher m = pattern.matcher(requestUrl);
		while(m.find()){
			System.out.println(m.group());
            return m.group();
        }
		return null;
	}

}
