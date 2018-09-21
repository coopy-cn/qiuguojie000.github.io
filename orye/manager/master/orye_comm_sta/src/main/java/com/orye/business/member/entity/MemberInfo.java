package com.orye.business.member.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.member_info              
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
type                 Integer(10)                 2          //卡类型1.年卡2.其他
member_name          String(50)                             //卡名称
amount               Integer(10)                 0          //价格（分）
count                Integer(10)                            //次数
img_url              String(255)                            //图片
remark               String(65535)                          //使用规则
valid_month          Integer(10)                 0          //有效月数，0为年卡
create_time          Date(19)                               //创建时间
*/
public class MemberInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	Integer type;
	private	String memberName;
	private	Integer amount;
	private	Integer count;
	private	String imgUrl;
	private	String remark;
	private	Integer validMonth;
	private	Date createTime;
	
	private Integer restCount;//剩余次数

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
	* member_name  String(50)  //卡名称    
	*/
	public String getMemberName(){
		return memberName;
	}
	
	/**
	* member_name  String(50)  //卡名称    
	*/
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	
	/**
	* amount  Integer(10)  0  //价格（分）    
	*/
	public Integer getAmount(){
		return amount;
	}
	
	/**
	* amount  Integer(10)  0  //价格（分）    
	*/
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	/**
	* count  Integer(10)  //次数    
	*/
	public Integer getCount(){
		return count;
	}
	
	/**
	* count  Integer(10)  //次数    
	*/
	public void setCount(Integer count){
		this.count = count;
	}
	
	/**
	* img_url  String(255)  //图片    
	*/
	public String getImgUrl(){
		return imgUrl;
	}
	
	/**
	* img_url  String(255)  //图片    
	*/
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
	
	/**
	* create_time  Date(19)  //创建时间    
	*/
	public Date getCreateTime(){
		return createTime;
	}
	
	/**
	* create_time  Date(19)  //创建时间    
	*/
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Integer getRestCount() {
		return restCount;
	}

	public void setRestCount(Integer restCount) {
		this.restCount = restCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getValidMonth() {
		return validMonth;
	}

	public void setValidMonth(Integer validMonth) {
		this.validMonth = validMonth;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}