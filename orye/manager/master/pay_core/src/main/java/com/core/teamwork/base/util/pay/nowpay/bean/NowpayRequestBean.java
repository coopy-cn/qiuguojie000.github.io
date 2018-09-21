package com.core.teamwork.base.util.pay.nowpay.bean;

public class NowpayRequestBean {
	/*preSign.appId = appID;
    preSign.mhtOrderStartTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
    preSign.mhtOrderNo = preSign.mhtOrderStartTime;
    preSign.mhtOrderName = "鼠标";
    preSign.mhtOrderType = "01";
    preSign.mhtCurrencyType = "156";
    preSign.mhtOrderAmt = "10";
    preSign.mhtOrderDetail = "关于支付的演示";
    preSign.mhtOrderTimeOut = "3600";
    preSign.notifyUrl = "http://localhost:10802/";
    preSign.mhtCharset = "UTF-8";
    preSign.mhtReserved = "test";
    preSign.consumerId = "456123";
    preSign.consumerName = "yuyang";*/
	//&consumerId=456123&consumerName=yuyang&mhtOrderNo=20160708171018&mhtReserved=test&payChannelType=13
	
	private String appId;
	private String mhtOrderStartTime;
	private String mhtOrderNo;
	private String mhtOrderName;
	private String mhtOrderType = "01";	//默认为普通消费
	private String mhtCurrencyType = "156";	//默认为人民币
	private int mhtOrderAmt;
	private String mhtOrderDetail;
	private int mhtOrderTimeOut = 3600; //默认为3600秒	
	private String notifyUrl;
	private String mhtCharset="UTF-8";
	private String mhtReserved;
	private String consumerId;
	private String consumerName;
	private String payChannelType = "13";	//默认为微信支付
	private String mhtSignType;
	private String mhtSignature;
	
	public String getPayChannelType() {
		return payChannelType;
	}
	public void setPayChannelType(String payChannelType) {
		this.payChannelType = payChannelType;
	}
	public String getMhtSignType() {
		return mhtSignType;
	}
	public void setMhtSignType(String mhtSignType) {
		this.mhtSignType = mhtSignType;
	}
	public String getMhtSignature() {
		return mhtSignature;
	}
	public void setMhtSignature(String mhtSignature) {
		this.mhtSignature = mhtSignature;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMhtOrderStartTime() {
		return mhtOrderStartTime;
	}
	public void setMhtOrderStartTime(String mhtOrderStartTime) {
		this.mhtOrderStartTime = mhtOrderStartTime;
	}
	public String getMhtOrderNo() {
		return mhtOrderNo;
	}
	public void setMhtOrderNo(String mhtOrderNo) {
		this.mhtOrderNo = mhtOrderNo;
	}
	public String getMhtOrderName() {
		return mhtOrderName;
	}
	public void setMhtOrderName(String mhtOrderName) {
		this.mhtOrderName = mhtOrderName;
	}
	public String getMhtOrderType() {
		return mhtOrderType;
	}
	public void setMhtOrderType(String mhtOrderType) {
		this.mhtOrderType = mhtOrderType;
	}
	public String getMhtCurrencyType() {
		return mhtCurrencyType;
	}
	public void setMhtCurrencyType(String mhtCurrencyType) {
		this.mhtCurrencyType = mhtCurrencyType;
	}
	public int getMhtOrderAmt() {
		return mhtOrderAmt;
	}
	public void setMhtOrderAmt(int mhtOrderAmt) {
		this.mhtOrderAmt = mhtOrderAmt;
	}
	public String getMhtOrderDetail() {
		return mhtOrderDetail;
	}
	public void setMhtOrderDetail(String mhtOrderDetail) {
		this.mhtOrderDetail = mhtOrderDetail;
	}
	public int getMhtOrderTimeOut() {
		return mhtOrderTimeOut;
	}
	public void setMhtOrderTimeOut(int mhtOrderTimeOut) {
		this.mhtOrderTimeOut = mhtOrderTimeOut;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getMhtCharset() {
		return mhtCharset;
	}
	public void setMhtCharset(String mhtCharset) {
		this.mhtCharset = mhtCharset;
	}
	public String getMhtReserved() {
		return mhtReserved;
	}
	public void setMhtReserved(String mhtReserved) {
		this.mhtReserved = mhtReserved;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	
}
