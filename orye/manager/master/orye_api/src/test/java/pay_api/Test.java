package pay_api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.core.teamwork.base.util.http.HttpUtil;
import com.orye.business.util.httpsUtil.HttpsUtil;

public class Test {
	/**
	* @Title: main 
	* @Description: 支付宝扫码支付测试类
	* @param @param args
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	*/
	private static String DateStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(date);
		return str;
	}
	
	private static Date DateStr(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date str = sdf.parse(date);
		return str;
	}
	
	
	public static void main(String[] args) throws Exception {
		/*String result = XXPayUtil.call4Post("http://127.0.0.1:3000/pay/test.htm");
    	System.out.println(result);*/
		
		/*String s = HttpsUtil.sendPost("http://127.0.0.1:3020/api/pay/create_order","params={}");
		System.out.println(s);*/
		
		/*PayRetryer pr = new PayRetryer();
		
		Callable<String> maySuccessTask = new Callable<String>() {
	        private int times = 0;
	        @Override
	        public String call() throws Exception {
	            System.out.println("called"+times);
	            times++;
	 
	            if (times == 1) {
	                throw new NullPointerException();
	            } else if (times == 10) {
	                return "success";
	            } else {
	                return "fail";
	            }
	        }
	    };
		
	    final long time = System.currentTimeMillis();
	    
		pr.retryer("success", 10, 10, maySuccessTask, new RetryListener() {
			
			@Override
			public <V> void onRetry(Attempt<V> attempt) {
				// TODO Auto-generated method stub
				System.out.println(System.currentTimeMillis()-time);
			}
		});*/
		
		String ss = HttpsUtil.doGetString("http://192.168.1.77:8083/order/pay.do?amount=0.01&ip=12.123.12.12&mch_id=0000000003&notify_url=http://www.baidu.com&order_id=111452000005&order_name=测试&pay_channel=2&system_type=1&sign=8d8619431d69df6634e07208fa9fa527");
		System.out.println(ss);
	}

	/**
	 * 回调测试
	 * @param paramStr 参数字符串{sign=cfa348bf1be16344ba770d17abbda9e0, result_code=200, pay_money=0.01, order_num=DD20171028175600821167170, pay_time=20171028175754, pay_discount_money=0.01, buss_order_num=201710281757476}
	 * @param notifyUrl	回调地址
	 * @throws Exception
	 */
	public static void notify(String paramStr,String notifyUrl) throws Exception{
		String [] array = paramStr.substring(1,paramStr.length()-1).split(",");
		Map<String, Object> param = new HashMap<>();
		for(int i=0; i<array.length; i++) {
		  String[] data = array[i].split("=");
		  param.put(data[0].trim(),data[1]);
		}
		String result = "";
		if(notifyUrl.startsWith("https")){
			result = HttpsUtil.doPostString(notifyUrl, param, "utf-8");
		}else{
			// 通知商户
			result = HttpUtil.httpPost(notifyUrl, param);
		}
		System.out.println("回调商户接收：【"+result+"】");
		System.out.println(result.toCharArray().length);
		System.out.println("SUCCESS".equals(result.toUpperCase()));
	}
}
