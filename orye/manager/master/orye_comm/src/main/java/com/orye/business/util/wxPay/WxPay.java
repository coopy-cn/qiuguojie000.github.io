package com.orye.business.util.wxPay;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orye.business.util.DecimalUtil;

public class WxPay {
	
	private static final Log logger = LogFactory.getLog(WxPay.class);
     /**
      * 小程序支付
      * @param mch_id		商户号
      * @param key			密钥
      * @param appid		appid
      * @param body			商品名称
      * @param orderid		订单号
      * @param money		支付金额
      * @param ip			服务器ip
      * @return
      */
      public static Map<String, String> xcxPay(String openid,String body
      		, String orderid, String money, String ip) {
      	Map<String,String> result = new HashMap<>();
      	result.put("code", "10001");
          Map<String, String> returnMap = new ConcurrentHashMap<String, String>();
          try {
              // 构造下单所需要的参数
              SortedMap<String, String> map = new TreeMap<String, String>();
              Collections.synchronizedSortedMap(map);
              map.put("appid", WeChatConstant.APPPAY_APPID); 
             
              map.put("mch_id", WeChatConstant.APPPAY_MCHID);
              //H5支付的交易类型为MWEB
              map.put("trade_type", "JSAPI");
             
              //随机字符串
              //String nonce_str = create_nonce_str().replaceAll("-", "");
              map.put("nonce_str",WeChatUtil.create_nonce_str().replaceAll("-", ""));
              
              // 商品描述
              map.put("body", body);
              
              map.put("openid", openid);
              
              // 商户订单号
              map.put("out_trade_no", orderid);
              
              // 总金额，单位是分
              map.put("total_fee", DecimalUtil.yuanToCents(money));
              
              //客户端ip
              map.put("spbill_create_ip", ip);
              
              // 异步通知地址
              map.put("notify_url", WeChatConstant.WX_NOTIFY_URL);
              
              // 生成sign
              map.put("sign", WeChatUtil.createSign(map));
              // 把参数转换为xml数据
              String params = XMLUtil.toXML(map);
              logger.error("=====微信提交预支付订单:\n"+params+" \n");
              // 提交数据，执行预支付
              //URLHanlder.post(wxConfig.getProperty("wx_unifiedorder"), params)
              returnMap = XMLUtil.toMap(WeChatUtil.post(WeChatConstant.UNIFIED_ORDER, params));
              
              System.out.println("=====微信返回的预支付订单信息:\n"+returnMap +" \n");
              if(checkParam(returnMap)){
              	if (returnMap!=null && "success".equalsIgnoreCase(returnMap.get("return_code")) && "success".equalsIgnoreCase(returnMap.get("result_code"))) {
              		
              		SortedMap<String, String> paramMap = new TreeMap<String, String>();
        			Collections.synchronizedSortedMap(paramMap);
        			paramMap.put("appId", returnMap.get("appid"));
        			paramMap.put("nonceStr", WeChatUtil.create_nonce_str().replaceAll("-", ""));
        			paramMap.put("package", "prepay_id="+returnMap.get("prepay_id"));
        			paramMap.put("signType", "MD5");
        			paramMap.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));
        			paramMap.put("paySign", WeChatUtil.createSign(paramMap));
        			result.putAll(paramMap);
        			result.put("code", "10000");
                  }else{
                  	result.put("msg", returnMap.toString());
                  }
              }else{
              	result.put("msg", "验签失败");
              }
          } catch (Exception e) {
          	result.put("msg", "请求微信错误");
          	e.printStackTrace();
          }
          return result;
      }
      /** <一句话功能简述>
       * <功能详细描述>验证返回参数
       * @param params
       * @param key
       * @return
       * @see [类、类#方法、类#成员]
       */
      public static boolean checkParam(Map<String,String> params){
          boolean result = false;
          if(params.containsKey("sign")){
              String sign = params.get("sign");
              params.remove("sign");
              StringBuilder buf = new StringBuilder((params.size() +1) * 10);
              buildPayParams(buf,params,false);
              String preStr = buf.toString();
              String signRecieve = MD5Util.sign(preStr, "&key=" + WeChatConstant.KEY, "utf-8");
              result = sign.equalsIgnoreCase(signRecieve);
          }
          return result;
      }
      /**
       * @author 
       * @param payParams
       * @return
       */
      public static void buildPayParams(StringBuilder sb,Map<String, String> payParams,boolean encoding){
          List<String> keys = new ArrayList<String>(payParams.keySet());
          Collections.sort(keys);
          for(String key : keys){
          	if(org.apache.commons.lang.StringUtils.isBlank(payParams.get(key))){
          		continue;
          	}
              sb.append(key).append("=");
              if(encoding){
                  sb.append(urlEncode(payParams.get(key)));
              }else{
                  sb.append(payParams.get(key));
              }
              sb.append("&");
          }
          sb.setLength(sb.length() - 1);
      }
      public static String urlEncode(String str){
          try {
              return URLEncoder.encode(str, "UTF-8");
          } catch (Throwable e) {
              return str;
          } 
      }
    
    public static void main(String[] args) {
    	/*String mch_id="1438851302";
    	String appId="wx2918c3abf2413f91";
    	String key="c88155a2d0c14464a30e238c3b2c6c8d";
//    	scanPay(key,appId,mch_id, "测试", System.currentTimeMillis()+"", "0.01", "119.123.227.63");
    	queryOrder(mch_id,key,appId, "hy_20180730112918889983382");*/
    	
    	String mch_id="1511008541";
    	String appId="wx0b5d11d8855824e6";
    	String key="7d1a8fdeacc4ef7272a16840819323bb";
    	String openid = "o4tLb4mTpUCLb47CuZ32hpNkOqxs";
    	xcxPay(openid, "测试", System.currentTimeMillis()+"", "0.01", "192.168.1.66");
    	
    	//{appid=wx0b5d11d8855824e6, mch_id=1511008541, nonce_str=TvJvEvCwhArIjJ2G, prepay_id=wx31154001601674f4eedf2b281623502515, result_code=SUCCESS, return_code=SUCCESS, return_msg=OK, sign=E442C3275D5C8C92AB6C390810ADECE4, trade_type=JSAPI}
//    	h5Pay("1512137101", "6194a391b65fcd1f0499fd42215882fa", "wxdd2dba60beae4a9b", "测试", System.currentTimeMillis()+"", "0.01", "192.168.1.66");
//    	refund("1480346522","abcdefghijk1234567890ABCDEFGHIJK","wxaa62e87aae59956b","81713511", "DD20171221113932986213092", System.currentTimeMillis()+"", "0.01", "0.01");
//    	String wxStr =clear("1480346522","abcdefghijk1234567890ABCDEFGHIJK","wxaa62e87aae59956b",null, "20180101");
    	/*String []wxClear = wxStr.split("\n");
    	for (int i = 1; i < wxClear.length-2; i++) {
			String str = wxClear[i];
			String orderInfo[] = str.split(",");
			// 微信对账文件24个字段
			if (orderInfo.length >= 24) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String s = orderInfo[19].replace("`", "");
				if(!StringUtils.isEmpty(s)){
					logger.error(s);
				}
			}
		}*/
    	/*for (int i = 0; i < 50; i++) {
			
		}*/
    }
}
