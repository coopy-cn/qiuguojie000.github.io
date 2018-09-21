package com.orye.business.member.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.member_user              
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
user_id              Long(19)                               //用户id，关联表course_user
open_id              String(64)                             //微信openid
mem_id               Long(19)                               //会员卡id，关联表member_info
count                Integer(10)                 0          //剩余次数
valid_date           Date(19)                               //到期时间
create_time          Date(19)                               //创建时间
*/
public class MemberUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	Long userId;
	private	String openId;
	private	Long memId;
	private	Integer count;
	private	Date validDate;
	private	Date createTime;

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
	* user_id  Long(19)  //用户id，关联表course_user    
	*/
	public Long getUserId(){
		return userId;
	}
	
	/**
	* user_id  Long(19)  //用户id，关联表course_user    
	*/
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	/**
	* open_id  String(64)  //微信openid    
	*/
	public String getOpenId(){
		return openId;
	}
	
	/**
	* open_id  String(64)  //微信openid    
	*/
	public void setOpenId(String openId){
		this.openId = openId;
	}
	
	/**
	* mem_id  Long(19)  //会员卡id，关联表member_info    
	*/
	public Long getMemId(){
		return memId;
	}
	
	/**
	* mem_id  Long(19)  //会员卡id，关联表member_info    
	*/
	public void setMemId(Long memId){
		this.memId = memId;
	}
	
	/**
	* count  Integer(10)  0  //剩余次数    
	*/
	public Integer getCount(){
		return count;
	}
	
	/**
	* count  Integer(10)  0  //剩余次数    
	*/
	public void setCount(Integer count){
		this.count = count;
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

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	
}