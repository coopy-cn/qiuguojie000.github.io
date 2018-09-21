package com.core.teamwork.base.util.pay.alipay.util;

import java.beans.PropertyDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.core.teamwork.base.util.pay.alipay.bean.AlipayRequestBean;
import com.core.teamwork.base.util.pay.alipay.config.AlipayConfig;
import com.core.teamwork.base.util.pay.alipay.sign.RSA;


/* *
 *类名：AlipayFunction
 *功能：支付宝接口公用函数类
 *详细：该类是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改
 *版本：3.3
 *日期：2012-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayCore {

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}
	
	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString2(Map<String, Object> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Object obj = params.get(key);
			String value = obj==null?"":obj.toString();
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}
	
	//将javabean实体类转为map类型，然后返回一个map类型的值
    public static Map<String, String> beanToMap(Object obj) { 
            Map<String, String> params = new HashMap<String, String>(0); 
            try { 
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
                for (int i = 0; i < descriptors.length; i++) { 
                    String name = descriptors[i].getName(); 
                    if (!"class".equals(name) && null != propertyUtilsBean.getNestedProperty(obj, name)) {
                    		params.put(name,"\""+propertyUtilsBean.getNestedProperty(obj, name).toString()+"\""); 
                    } 
                } 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            return params; 
    }
    
    /**
     * MAP值不加双引号
     * @param obj
     * @return
     */
    public static Map<String, String> beanToMapNoQuote(Object obj) { 
            Map<String, String> params = new HashMap<String, String>(0); 
            try { 
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
                for (int i = 0; i < descriptors.length; i++) { 
                    String name = descriptors[i].getName(); 
                    if (!"class".equals(name) && null != propertyUtilsBean.getNestedProperty(obj, name)) {
                    		params.put(name,propertyUtilsBean.getNestedProperty(obj, name).toString()); 
                    } 
                } 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            return params; 
    }
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//参数
//		AlipayRequestBean bean = new AlipayRequestBean();
//		bean.setBody("商品详情");
//		bean.setNotify_url("支付宝请求我们的回调路径http://");
//		bean.setOut_trade_no("订单号");
//		bean.setSubject("商品名称");
//		//价格
//		bean.setTotal_fee(123.01);
//		//生成签名
//		String sign = createSign(beanToMap(bean));
//		//加入签名
//		bean.setSign(sign);
//		//拼接完成的字符串
//		System.out.println(createLinkString(beanToMap(bean)));
		
//		AlipayRequestBean bean = new AlipayRequestBean();
//		bean.setBody("该测试商品的详细描述");
//		bean.setNotify_url("http://127.0.0.1/pay/getAlipayNotify");
//		bean.setOut_trade_no("IWD146658037435035920");
//		bean.setSubject("测试商品");
//		//价格
//		bean.setTotal_fee(0.01);
//		//生成签名
//		String sign = createSign(beanToMap(bean));
//		System.out.println(sign);
		
		AlipayRequestBean bean = new AlipayRequestBean();
		bean.setBody("该测试商品的详细描述");
		bean.setNotify_url("http://42.51.172.52/cpa_center/pay/getNotifyOfAlipay.do");
		bean.setOut_trade_no("IWD3848000000098784883412");
		bean.setSubject("测试商品");
		//价格
		bean.setTotal_fee(0.01);
		bean.setSign(AlipayCore.createSign(AlipayCore.beanToMap(bean)));
		//生成签名
//		String sign = createSign(beanToMap(bean));
		System.out.println(AlipayCore.createLinkString(AlipayCore.beanToMap(bean)));
	}

	/**
	 * 根据参数生成签名
	 * @param Params 一定是过滤了sign与sign_type参数的字符串
	 * @return
	 */
	public static String createSign(Map<String, String> Params) {
//		// 过滤空值、sign与sign_type参数
		Map<String, String> sParaNew = paraFilter(Params);
//		// 获取待签名字符串
		String preSignStr = createLinkString(sParaNew);
		return RSA.sign(preSignStr, AlipayConfig.private_key,AlipayConfig.input_charset);
	}
	

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord
	 *            要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(AlipayConfig.log_path + "alipay_log_"
					+ System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
