package com.orye.business.util.wxPay;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * 微信额外用到的方法集成
 * @ClassName: WeChatUtil 
 * @Description: 
 * @author yangyu
 * @date 2016年11月10日 下午3:37:48
 */
public class WeChatUtil {

	private static Logger logger = Logger.getLogger(WeChatUtil.class);
	
	/**
	 * 微信请求xml请求方法
	 * @param url
	 * @param content 默认UTF-8
	 * @return
	 */
	public static InputStream post(String url,String content){
    	return post(url, content, "UTF-8");
    }
	
	protected static InputStream post(String url,String content,String charset){
		URL uri = null;
		try {
			uri = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStream out = conn.getOutputStream();
			OutputStreamWriter ow = new OutputStreamWriter(out,charset);
			ow.write(content);
			ow.flush();
			ow.close();
			out.flush();
			out.close();
			int len = conn.getContentLength();
			if(len>0){
				return conn.getInputStream();
			}
		} catch (Exception e) {
			logger.error("=====请求微信HTTP异常:", e);
		}
		return null;
	}
	
	/**
	 * 创建微信的签名
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String createSign(Map<String, String> map) throws Exception {
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String k = it.next();
            String v = map.get(k);
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WeChatConstant.KEY);
        String sign = SecurityUtil.md5(sb.toString()).toUpperCase();
        return sign;
    }
	
	/**
     * 随机算法
     * @return
     */
    public static String create_nonce_str() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
}
