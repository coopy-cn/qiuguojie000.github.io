package com.orye.manger.controller.pay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.orye.business.course.entity.CourseOrder;
import com.orye.business.course.service.CourseOrderService;
import com.orye.business.pay.entity.PayOrder;
import com.orye.business.pay.service.PayOrderService;
import com.orye.business.util.httpsUtil.HttpsUtil;
import com.orye.business.util.wxPay.WxPay;
import com.orye.business.util.wxPay.XMLUtil;

@Controller
@RequestMapping("/callBack/*")
public class CallBackController {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CourseOrderService courseOrderService;
	@Autowired
	private PayOrderService payOrderService;
	/**
	 * 微信回调
	 * @param map
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="/wx")
	public void wx(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
            String resString = XMLUtil.parseRequst(request);
            logger.error("微信官方通知内容：" + resString);
            
            String respString = "fail";
            if(resString != null && !"".equals(resString)){
                Map<String,String> map = XMLUtil.toMap(resString.getBytes(), "utf-8");
                String out_trade_no = map.get("out_trade_no");// 商户订单号
                if(out_trade_no!=null&&!"".equals(out_trade_no)){
                	Map<String,Object> param = new HashMap<>();
                	param.put("orderId", out_trade_no);
	                CourseOrder order = courseOrderService.detail(param);
	                
	                PayOrder payorder = payOrderService.detail(param);
	                
	                if(payorder!=null){
		                if(map.containsKey("sign")){
		                    if(!WxPay.checkParam(map)){
		                        logger.error("验证签名不通过");
		                    }else{
	                        	String return_code = map.get("return_code");
    	                        if(return_code != null && "SUCCESS".equals(return_code)){
    	                            String result_code = map.get("result_code");
    	                            if(result_code != null && "SUCCESS".equals(result_code)){
    	                            	boolean con = payOrderService.callBack(order, payorder, map);
    	                            	if(!con){
    	                            		return;
    	                            	}
    	                            	/*String modId ="xQPtTdUXHWa4nVkzLvHUXnf4IQw2yPcDyAlJ9c0a3lU";
    	                            	String access_token = "13_YdFrPAuf10rp-3_AnIilW-PPuFI-6MnzJpJXoZg9WFXT-vXHnzfRBRa11GiToXJjTYPQ_aiAK4uhAj65y3kyp2Q9CIrLxVYa5RruNkL_FfJn0Fl7T5kYVX9-BsePhsWzTnFYrGq01mBuiny4HAHjAAAVOV";
    	                            	
    	                            	String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+access_token;
    	                            	
    	                            	Map<String,Object> paramMap = new HashMap<>();
    	                            	paramMap.put("template_id", modId);
    	                            	paramMap.put("touser", "oODgc5AHkLaDdTega-3K1aEEqsjQ");
    	                            	paramMap.put("form_id", modId);
    	                            	JSONObject obj = new JSONObject();
    	                            	JSONObject keyword1 = new JSONObject();
    	                            	keyword1.put("value", out_trade_no);
    	                            	
    	                            	JSONObject keyword2 = new JSONObject();
    	                            	keyword2.put("value", out_trade_no);
    	                            	
    	                            	JSONObject keyword3 = new JSONObject();
    	                            	keyword3.put("value", out_trade_no);
    	                            	
    	                            	JSONObject keyword4 = new JSONObject();
    	                            	keyword4.put("value", out_trade_no);
    	                            	
    	                            	
    	                            	obj.put("keyword1", keyword1);
    	                            	obj.put("keyword2", order.getAmount());
    	                            	obj.put("keyword3", DateUtil.DateToString(parse, DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
    	                            	obj.put("keyword4", "课程预约");
    	                            	
    	                            	paramMap.put("data", obj);
    	                            	paramMap.put("emphasis_keyword", "keyword1.DATA");
    	                            	String result = HttpsUtil.doPostString(url, paramMap, "utf-8");
    	                            	System.out.println(result);*/
    	                            	
    	                            	respString = "<xml><return_code>SUCCESS</return_code></xml>";
    	                            } 
    	                        }
		                    }
		                }
	                }
                }
            }
            response.getWriter().write(respString);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		String secret = "05095ff8c9b00758d879cd6d8ec72099";
		String appId = "wxf9535006f414ba26";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
		String result = HttpsUtil.doGetString(url);
		System.out.println(result);
	}
}
