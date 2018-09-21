package com.core.teamwork.base.util.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: HttpUtil
 * @Description: HTTP请求工具类
 * @author buyuer
 * @date 2015年6月4日 下午2:48:24
 * 
 */
@SuppressWarnings("deprecation")
public class HttpUtil {

	private static final Logger LOGGER = Logger.getLogger(HttpUtil.class);
	
	private static Map<String, String> headers = new HashMap<String, String>();
	static {
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
		headers.put("Accept-Language", "zh-cn,zh;q=0.5");
		headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		headers.put(
				"Accept",
				" image/gif, image/x-xbitmap, image/jpeg, "
						+ "image/pjpeg, application/x-silverlight, application/vnd.ms-excel, "
						+ "application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Accept-Encoding", "gzip, deflate");
	}

	/**
	 * 异常或者没拿到返回结果的情况下,result为""
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String httpPost(String url, Map<String, Object> param) {
//		LOGGER.info("HTTP POST ==> URL:"+url+"\tPARAMS:"+param.toString());
		DefaultHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		StringBuffer suf = new StringBuffer();
		try {
			httpclient = new DefaultHttpClient();
			// 设置cookie的兼容性---考虑是否需要
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpPost = new HttpPost(url);
			// 设置各种头信息
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 传入各种参数
			if (null != param) {
				for (Entry<String, Object> set : param.entrySet()) {
					String key = set.getKey();
					String value = set.getValue() == null ? "" : set.getValue()
							.toString();
					nvps.add(new BasicNameValuePair(key, value));
					suf.append(" [" + key + "-" + value + "] ");
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			// 设置连接超时时间
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
					20000);
			// 设置读数据超时时间
			HttpConnectionParams.setSoTimeout(httpPost.getParams(), 20000);
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return "";
			} else {
				entity = response.getEntity();
				if (null != entity) {
					Header encodingHeader = entity.getContentEncoding();
					if(encodingHeader!=null&&encodingHeader.getValue()!=null&&encodingHeader.getValue().indexOf("gzip")>-1){
						entity = new GzipDecompressingEntity(entity);
					}
					byte[] bytes = EntityUtils.toByteArray(entity);
					result = new String(bytes, "UTF-8");
				} else {
				}
				return result;
			}
		} catch (Exception e) {
			LOGGER.error("HTTP POST ERROR:",e);
			return "";
		} finally {
			if (null != httpclient) {
				httpclient.getConnectionManager().shutdown();
			}
		}
	}
	
	
	/**
	 * 异常或者没拿到返回结果的情况下,result为""
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static Integer getCode(String url, Map<String, Object> param) {
		LOGGER.info("HTTP POST ==> URL:"+url+"\tPARAMS:"+param.toString());
		int statusCode = 0;
		DefaultHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		try {
			 httpclient = new DefaultHttpClient();
			// 设置cookie的兼容性---考虑是否需要
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpPost = new HttpPost(url);
			// 设置各种头信息
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 传入各种参数
			if (null != param) {
				for (Entry<String, Object> set : param.entrySet()) {
					String key = set.getKey();
					String value = set.getValue() == null ? "" : set.getValue()
							.toString();
					nvps.add(new BasicNameValuePair(key, value));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			// 设置连接超时时间
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
					20000);
			// 设置读数据超时时间
			HttpConnectionParams.setSoTimeout(httpPost.getParams(), 20000);
			response = httpclient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			LOGGER.error("HTTP POST ERROR:",e);
			statusCode = 500;
		} finally {
			if (null != httpclient) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return statusCode;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: HttpGet
	 * @Description: get请求
	 * @param url
	 *            请求url
	 * @param header
	 *            请求头
	 * @return 请求结果
	 */
	public static String HttpGet(String url, Map<String, Object> header) {
		String result = "";
		BufferedReader in = null;
		try {
			// set url
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			// set header
			if (null != header && !header.isEmpty()) {
				for (String key : header.keySet()) {
					connection.setRequestProperty(key, header.get(key)
							.toString());
				}
			}
			// get response body
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * post SSL请求
	 * @param paramMap
	 * @param reqUrl
	 * @return
	 * @throws Exception
	 */
	public static String sendRequest(String reqUrl ,Map<String, Object> paramMap) throws Exception {
		HttpEntity reqEntity = null;
		List<NameValuePair> formParams = convertNameValuePairToList(paramMap);
		
		reqEntity = new UrlEncodedFormEntity(formParams, "utf-8");
		HttpPost httpPost = new HttpPost(reqUrl);
		httpPost.setEntity(reqEntity);
		
		String result = SSLClient.exctueRequest(httpPost);
		if (result == null) {
			return null;
		}
		return  result;
//		JSONObject retJson = JSON.parseObject(result);
//		return retJson;
	}
	
	private static List<NameValuePair> convertNameValuePairToList(Map<String, Object> requestParams) {
        if (requestParams == null || requestParams.isEmpty()) {
            return null;
        }
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        return formParams;
    }
	
	/**
	 * @Description: 文件下载
	 * @param @param url 文件地址
	 * @param @param path   文件路径
	 * @return void  
	 * @throws
	 * @author pengzhihao
	 * @date 2016-6-21上午9:55:29
	 */
	public static void httpFilePost(String url, String path){
		org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
		GetMethod httpGet = new GetMethod(url);  
		try {
			client.executeMethod(httpGet);
			if(httpGet.getStatusCode() == 404){
				LOGGER.error("下载资源不存在！");
				return;
			}
			
			File file = new File(path);
			if(!file.exists() && !file.isDirectory()){
				file.getParentFile().mkdirs();
			}
			InputStream in = httpGet.getResponseBodyAsStream();  
			FileOutputStream out = new FileOutputStream(file);  
			 
			byte[] b = new byte[1024];  
			int len = 0;  
			while((len=in.read(b))!= -1){  
			    out.write(b,0,len);  
			}  
			in.close();  
			out.close();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{  
            httpGet.releaseConnection();  
        }
	}
}
