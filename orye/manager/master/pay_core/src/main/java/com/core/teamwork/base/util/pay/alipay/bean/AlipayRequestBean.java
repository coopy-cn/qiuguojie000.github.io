package com.core.teamwork.base.util.pay.alipay.bean;

import com.core.teamwork.base.util.pay.alipay.config.AlipayConfig;


public class AlipayRequestBean {

	/**
	 * 接口名称
	 */
	private String service = AlipayConfig.service;
	/**
	 * 合作者身份ID
	 */
	private String partner = AlipayConfig.partner;
	/**
	 * 参数编码字符集
	 */
	private String _input_charset = AlipayConfig.input_charset;
	/**
	 * 服务器异步通知页面路径
	 */
	private String notify_url;
	/**
	 * 商户网站唯一订单号
	 */
	private String out_trade_no;
	/**
	 * 商品名称
	 */
	private String subject;
	/**
	 * 支付类型
	 */
	private String payment_type = "1";
	/**
	 * 卖家支付宝账号
	 */
	private String seller_id= AlipayConfig.seller_id;
	/**
	 * 总金额
	 */
	private double total_fee;
	
	/**
	 * 商品详情
	 */
	private String body;
	
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 签名方式
	 */
	private String sign_type = AlipayConfig.sign_type;
	
	/**
	 * 商品类型
	 */
//	private String goods_type = "1";

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getService() {
		return service;
	}

	public String getPartner() {
		return partner;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public String getSeller_id() {
		return seller_id;
	}

//	public String getGoods_type() {
//		return goods_type;
//	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}
	
	
}
