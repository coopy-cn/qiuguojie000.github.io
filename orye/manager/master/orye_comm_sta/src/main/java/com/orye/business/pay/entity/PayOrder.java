package com.orye.business.pay.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.pay_order                
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
open_id              String(64)                             //微信用户id
order_id             String(64)                             //订单号
transaction_id       String(64)                             //微信订单号
order_name           String(255)                            //订单名称
amount               Integer(10)                            //金额（分）
type                 Integer(10)                 2          //类型1.购卡2.预约课程
mem_id               Long(19)                               //卡id，type=1时有值
status               Integer(10)                 2          //支付状态1.支付成功2未支付
create_time          Date(19)                               //交易时间
*/
public class PayOrder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	String openId;
	private	String orderId;
	private	String transactionId;
	private	String orderName;
	private	Integer amount;
	private	Integer type;
	private	Long memId;
	private	Integer status;
	private	Date createTime;
	
	private String memberName;//卡名称

	/**
	* id  Long(19)  NOTNULL  //    
	*/
	public Long getId(){
		return id;
	}
	
	/**
	* id  Long(19)  NOTNULL  //    
	*/
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	* open_id  String(64)  //微信用户id    
	*/
	public String getOpenId(){
		return openId;
	}
	
	/**
	* open_id  String(64)  //微信用户id    
	*/
	public void setOpenId(String openId){
		this.openId = openId;
	}
	
	/**
	* order_id  String(64)  //订单号    
	*/
	public String getOrderId(){
		return orderId;
	}
	
	/**
	* order_id  String(64)  //订单号    
	*/
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	
	/**
	* transaction_id  String(64)  //微信订单号    
	*/
	public String getTransactionId(){
		return transactionId;
	}
	
	/**
	* transaction_id  String(64)  //微信订单号    
	*/
	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}
	
	/**
	* order_name  String(255)  //订单名称    
	*/
	public String getOrderName(){
		return orderName;
	}
	
	/**
	* order_name  String(255)  //订单名称    
	*/
	public void setOrderName(String orderName){
		this.orderName = orderName;
	}
	
	/**
	* amount  Integer(10)  //金额（分）    
	*/
	public Integer getAmount(){
		return amount;
	}
	
	/**
	* amount  Integer(10)  //金额（分）    
	*/
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	/**
	* type  Integer(10)  2  //类型1.购卡2.预约课程    
	*/
	public Integer getType(){
		return type;
	}
	
	/**
	* type  Integer(10)  2  //类型1.购卡2.预约课程    
	*/
	public void setType(Integer type){
		this.type = type;
	}
	
	/**
	* mem_id  Long(19)  //卡id，type=1时有值    
	*/
	public Long getMemId(){
		return memId;
	}
	
	/**
	* mem_id  Long(19)  //卡id，type=1时有值    
	*/
	public void setMemId(Long memId){
		this.memId = memId;
	}
	
	/**
	* status  Integer(10)  2  //支付状态1.支付成功2未支付    
	*/
	public Integer getStatus(){
		return status;
	}
	
	/**
	* status  Integer(10)  2  //支付状态1.支付成功2未支付    
	*/
	public void setStatus(Integer status){
		this.status = status;
	}
	
	/**
	* create_time  Date(19)  //交易时间    
	*/
	public Date getCreateTime(){
		return createTime;
	}
	
	/**
	* create_time  Date(19)  //交易时间    
	*/
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
}