package com.cn.jay.business.data1.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.g_account                
--------------------------------------------------------
id                   Long(20)           NOTNULL             //
account              String(50)         NOTNULL             //帐号
userId               Long(19)                               //玩家ID
pwd                  String(10)         NOTNULL  111111     //
userName             String(50)                             //真实姓名
cardId               String(50)                             //身份证号码
createTime           Date(19)           NOTNULL  CURRENT_TIMESTAMP //创建时间
*/
public class Gaccount implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	String account;
	private	Long userId;
	private	String pwd;
	private	String userName;
	private	String cardId;
	private	Date createTime;

	/**
	* id  Long(20)  NOTNULL  //    
	*/
	public Long getId(){
		return id;
	}
	
	/**
	* id  Long(20)  NOTNULL  //    
	*/
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	* account  String(50)  NOTNULL  //帐号    
	*/
	public String getAccount(){
		return account;
	}
	
	/**
	* account  String(50)  NOTNULL  //帐号    
	*/
	public void setAccount(String account){
		this.account = account;
	}
	
	/**
	* userId  Long(19)  //玩家ID    
	*/
	public Long getUserId(){
		return userId;
	}
	
	/**
	* userId  Long(19)  //玩家ID    
	*/
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	/**
	* pwd  String(10)  NOTNULL  111111  //    
	*/
	public String getPwd(){
		return pwd;
	}
	
	/**
	* pwd  String(10)  NOTNULL  111111  //    
	*/
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	
	/**
	* userName  String(50)  //真实姓名    
	*/
	public String getUserName(){
		return userName;
	}
	
	/**
	* userName  String(50)  //真实姓名    
	*/
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	/**
	* cardId  String(50)  //身份证号码    
	*/
	public String getCardId(){
		return cardId;
	}
	
	/**
	* cardId  String(50)  //身份证号码    
	*/
	public void setCardId(String cardId){
		this.cardId = cardId;
	}
	
	/**
	* createTime  Date(19)  NOTNULL  CURRENT_TIMESTAMP  //创建时间    
	*/
	public Date getCreateTime(){
		return createTime;
	}
	
	/**
	* createTime  Date(19)  NOTNULL  CURRENT_TIMESTAMP  //创建时间    
	*/
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
}