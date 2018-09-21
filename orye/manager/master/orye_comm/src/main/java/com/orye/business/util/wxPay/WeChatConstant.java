package com.orye.business.util.wxPay;

import com.core.teamwork.base.util.ReadProChange;


public class WeChatConstant {

	protected static final String APPPAY_APPID = "wx0b5d11d8855824e6";
	
	protected static final String APPPAY_MCHID = "1511008541";
	
	/**
	 * 微信回调地址
	 */
	public static final String WX_NOTIFY_URL = ReadProChange.getValue("local_name")+"/callBack/wx.do";
	
	/**
	 * 网页或公众号的支付类型
	 */
	protected static final String TRADE_TYPE_JSAPI = "JSAPI";
	
	protected static final String KEY = "7d1a8fdeacc4ef7272a16840819323bb";
	
	
	/**
	 * 微信下单地址
	 */
	protected static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
}
