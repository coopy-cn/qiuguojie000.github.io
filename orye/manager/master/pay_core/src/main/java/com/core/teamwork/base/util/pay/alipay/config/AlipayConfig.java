package com.core.teamwork.base.util.pay.alipay.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.core.teamwork.base.util.ReadPro;
import com.core.teamwork.base.util.pay.alipay.util.AlipayCore;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串  点游 ：2088421676946254   爱内测：2088421410617033
	public static String partner = ReadPro.getValue("alipayPartner","2088421676946254");
	// 卖家支付宝名称 爱内测：2644930243@qq.com  点游：sunshuang@idianyou.cn
	public static String seller_id = ReadPro.getValue("alipaySellerId","sunshuang@idianyou.cn");
	
	//商户私钥
	//爱内测：MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK57RzKfmwzlSMEvx5bmTO+9xm0ufGUjAdfKNa/V4r6+bgR5s8d+dxU033Y25AZ/2/7DzI65EUJLQUvnrCnLMczcnu70t8BDRiwZ/xBXHgCpOrnGuGgmuxaUVBlsfEolLe8WFlgNukellzOWB3ZZX5AjVZfbw5hGRVODqhpm3ew9AgMBAAECgYApybyzkyY/YGqcWiUjLuUeRgVQAOIqFshKNlPFWSSFKnaO+9bi4JcErTUXyu2x4M1/psVHKWvIxN1OoJ3LJK84TAyKIrhO7wBmTn9gLXUYmFRZwAMHjgLSTfg24dgvzs607zx8a/IvEoTasydo6j9XAnmdF2X9cNmzfWzioIeQwQJBANwo6st8jReoLfPL3bB28nezYgatVBX0kaFwvfjh9e9EnhGTROdJwMSyZMwDyAFB6KPsnRnnxsAizcrchedO2SsCQQDK4rwmD1T5yPbcQZJ5a7geh08RQPkrfCQe0Yhu67iQv8NZ0noXU6RKQcEWs6GHvI6tVBwktila9dPko03SHcw3AkA1Tx8TaLRNXybJWrryaMrrWx9IWJ38tvywZ7lIU4aWijnwLYXIr14jAsNPdzbtvwTS9I9R34KLsaG9FkrCKKCDAkB4ldqOJH61F6ThtHeCg2ujeXidIKj4F4jAaHlWs/O21MMfF+JJCwWSvRIulAVlz8RdcKhpiXt6/LE8K3ONdvhxAkEAufSqJSD4zV6Tqsuga8e4T9Ts4lX8g1XGnZvA9RLyoKE5VtxXhoQuKGSGlr3O82iClr3RcCVd2Ctm8hQ+Yys5pQ==
	//点游：   	MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIfkLu0Jk7zp4M/bCpcsWENV9LV694X8f5Rvtzm4XvGvbdf7OjBLDwQJUV7NVmKsItn/qmWamtasYItczTq18NnsFTzgIc+2SnI77GivE5kdTQPZUDri6znzxqDc4r800YKuFPN0YjODLUXvI7H4fKlNiyWonCgqcnLovxhjYOJPAgMBAAECgYBbgxju2/I32DBjCZlaMKzHu8ztof+qpV2tMoZrQnP9wivwtOh8uS9FgpjfhNxV2qIluHyhq7n2M2NYtogmr3uf425JolH5DBneWuZDbFS7C9/OtEj9lYrvsi/acwi/96yFs5IBeaepA7j89vgh7/rbqSel5+JuyU7gaahX9SONIQJBAOzB9T7aE9KBE9WbH0Xfp3+I0NCfqOw+LzJ0GugqYF+HqRtRERIZSpYm9uddNyOmVM5FAbNcwmQOvZnIhMkJAskCQQCS75MIfzPAUSIL8R+5mOBcrf3asYK3e2rOIew/quJc+6C0WhOqxeg4x4UaYINYjP2MHs6K1FPmNowKXKUK0XBXAkBVdno5suze2ts3CyTRyzciUVfXRxrqFFRjKQghcFKTiVvg0XWRC/Ihm3Ua9UoXI06PmtdPUJeXGL8WSQytqQQJAkB3Wrnxtfngp0bjsmrVxPPC9OZ25ahLyKm6ZV3VXcbgRtmEhoLhPj9jF9br1Sf8HxK55jBaHx1DrF1W8WhzACujAkBI4ulkKhZuFTAuaDApJRSSJIgFTdIQ89cn1r7rGHhsacgjxTXG/J+Vaw0BJ3XsJFhf+ZHJAqjTTvGNZkri+wW2
	public static String private_key = ReadPro.getValue("alipayPrivateKey","MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIfkLu0Jk7zp4M/bCpcsWENV9LV694X8f5Rvtzm4XvGvbdf7OjBLDwQJUV7NVmKsItn/qmWamtasYItczTq18NnsFTzgIc+2SnI77GivE5kdTQPZUDri6znzxqDc4r800YKuFPN0YjODLUXvI7H4fKlNiyWonCgqcnLovxhjYOJPAgMBAAECgYBbgxju2/I32DBjCZlaMKzHu8ztof+qpV2tMoZrQnP9wivwtOh8uS9FgpjfhNxV2qIluHyhq7n2M2NYtogmr3uf425JolH5DBneWuZDbFS7C9/OtEj9lYrvsi/acwi/96yFs5IBeaepA7j89vgh7/rbqSel5+JuyU7gaahX9SONIQJBAOzB9T7aE9KBE9WbH0Xfp3+I0NCfqOw+LzJ0GugqYF+HqRtRERIZSpYm9uddNyOmVM5FAbNcwmQOvZnIhMkJAskCQQCS75MIfzPAUSIL8R+5mOBcrf3asYK3e2rOIew/quJc+6C0WhOqxeg4x4UaYINYjP2MHs6K1FPmNowKXKUK0XBXAkBVdno5suze2ts3CyTRyzciUVfXRxrqFFRjKQghcFKTiVvg0XWRC/Ihm3Ua9UoXI06PmtdPUJeXGL8WSQytqQQJAkB3Wrnxtfngp0bjsmrVxPPC9OZ25ahLyKm6ZV3VXcbgRtmEhoLhPj9jF9br1Sf8HxK55jBaHx1DrF1W8WhzACujAkBI4ulkKhZuFTAuaDApJRSSJIgFTdIQ89cn1r7rGHhsacgjxTXG/J+Vaw0BJ3XsJFhf+ZHJAqjTTvGNZkri+wW2");
	
	// 支付宝的公钥，无需修改该值
	//爱内测：MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB
	//点游：  	MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB
	public static String ali_public_key = ReadPro.getValue("alipayAliPublicKey","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB");

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String sign_type = "RSA";
	
	/**
	 * 接口名称固定值
	 */
	public static String service = ReadPro.getValue("alipayService","mobile.securitypay.pay");
	
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "123");
		System.out.println(AlipayCore.createSign(map));
		try {
			System.out.println(URLEncoder.encode(AlipayCore.createSign(map),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
