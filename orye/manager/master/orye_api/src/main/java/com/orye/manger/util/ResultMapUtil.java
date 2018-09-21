package com.orye.manger.util;

import java.util.HashMap;
import java.util.Map;

public class ResultMapUtil {
	
	/**
	 * 服务器异常
	 */
	public static final Map<String,String> ERROR_500_CODE = new HashMap<String, String>(){{
		put("code", "500");
		put("msg", "服务器异常");
	}};
	
	/**
	 * 商户号无效
	 */
	public static final Map<String,String> MCH_INVALID = new HashMap<String, String>(){{
		put("code", "1001");
		put("msg", "商户号无效");
	}};
	
	/**
	 * 商户签名错误
	 */
	public static final Map<String,String> SIGNATURE_ERROR = new HashMap<String, String>(){{
		put("code", "1002");
		put("msg", "商户签名错误");
	}};
	
	/**
	 * 缺少参数
	 */
	public static final Map<String,String> PARA_FAILED_CODE = new HashMap<String, String>(){{
		put("code", "1003");
		put("msg", "缺少参数");
	}};
	
	/**
	 * 支付金额错误
	 */
	public static final Map<String,String> MOENY_ERROR = new HashMap<String, String>(){{
		put("code", "1004");
		put("msg", "支付金额错误");
	}};
	
	/**
	 * 超过单笔额度和每日额度
	 */
	public static final Map<String,String> RATE_ORDER_ERROR = new HashMap<String, String>(){{
		put("code", "1005");
		put("msg", "超过单笔额度和每日额度");
	}};
	
	/**
	 * 支付通道未配置
	 */
	public static final Map<String,String> RATE_TYPE_ERROR = new HashMap<String, String>(){{
		put("code", "1006");
		put("msg", "支付通道未配置");
	}};
	
	/**
	 * 渠道下单错误
	 */
	public static final Map<String,String> RATE_FAIL = new HashMap<String, String>(){{
		put("code", "1007");
		put("msg", "渠道下单错误");
	}};
	
	/**
	 * 超过单笔限额
	 */
	public static final Map<String,String> PAY_TYPE_ONE_QUOTA = new HashMap<String, String>(){{
		put("code", "1008");
		put("msg", "超过单笔限额");
	}};
	
	/**
	 * 订单号重复
	 */
	public static final Map<String,String> PAY_REPEAT_FAIL = new HashMap<String, String>(){{
		put("code", "1009");
		put("msg", "订单号重复");
	}};
	
	/**
	 * 支付方式参数错误
	 */
	public static final Map<String,String> PAY_TYPE_NOT_BLANK = new HashMap<String, String>(){{
		put("code", "1010");
		put("msg", "支付方式参数错误");
	}};
	
	/**
	 * 订单不存在
	 */
	public static final Map<String,String> TRANSACTION_NOT_EXIST = new HashMap<String, String>(){{
		put("code", "2001");
		put("msg", "订单不存在");
	}};
	
	/**
	 * 订单未支付
	 */
	public static final Map<String,String> NOTPAY_FAILED_CODE = new HashMap<String, String>(){{
		put("code", "2002");
		put("msg", "订单未支付");
	}};
	
	/**
	 * 支付方式
	 */
	public static final Map<String,String> pay_type = new HashMap<String, String>(){{
		put("1", "WAP");
		put("2", "SCAN");
	}};
}
