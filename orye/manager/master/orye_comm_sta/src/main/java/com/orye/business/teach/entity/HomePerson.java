package com.orye.business.teach.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.home_person              
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
type                 Integer(10)                 1          //1.爸爸2.妈妈3.小孩
open_id              String(50)                             //微信openid
name                 String(50)                             //姓名
sex                  Integer(10)                 1          //性别1男2女
phone                String(20)                             //手机号
email                String(50)                             //电子邮箱,type=1时有
address              String(255)                            //地址,type=1时有
birth_date           Date(19)                               //出生日期,type=3时有
create_time          Date(19)                               //
*/
public class HomePerson implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	Integer type;
	private	String openId;
	private	String name;
	private	Integer sex;
	private	String phone;
	private	String email;
	private	String address;
	private	Date birthDate;
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
	* type  Integer(10)  1  //1.爸爸2.妈妈3.小孩    
	*/
	public Integer getType(){
		return type;
	}
	
	/**
	* type  Integer(10)  1  //1.爸爸2.妈妈3.小孩    
	*/
	public void setType(Integer type){
		this.type = type;
	}
	
	/**
	* open_id  String(50)  //微信openid    
	*/
	public String getOpenId(){
		return openId;
	}
	
	/**
	* open_id  String(50)  //微信openid    
	*/
	public void setOpenId(String openId){
		this.openId = openId;
	}
	
	/**
	* name  String(50)  //姓名    
	*/
	public String getName(){
		return name;
	}
	
	/**
	* name  String(50)  //姓名    
	*/
	public void setName(String name){
		this.name = name;
	}
	
	/**
	* sex  Integer(10)  1  //性别1男2女    
	*/
	public Integer getSex(){
		return sex;
	}
	
	/**
	* sex  Integer(10)  1  //性别1男2女    
	*/
	public void setSex(Integer sex){
		this.sex = sex;
	}
	
	/**
	* phone  String(20)  //手机号    
	*/
	public String getPhone(){
		return phone;
	}
	
	/**
	* phone  String(20)  //手机号    
	*/
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	/**
	* email  String(50)  //电子邮箱,type=1时有    
	*/
	public String getEmail(){
		return email;
	}
	
	/**
	* email  String(50)  //电子邮箱,type=1时有    
	*/
	public void setEmail(String email){
		this.email = email;
	}
	
	/**
	* address  String(255)  //地址,type=1时有    
	*/
	public String getAddress(){
		return address;
	}
	
	/**
	* address  String(255)  //地址,type=1时有    
	*/
	public void setAddress(String address){
		this.address = address;
	}
	
	/**
	* birth_date  Date(19)  //出生日期,type=3时有    
	*/
	public Date getBirthDate(){
		return birthDate;
	}
	
	/**
	* birth_date  Date(19)  //出生日期,type=3时有    
	*/
	public void setBirthDate(Date birthDate){
		this.birthDate = birthDate;
	}
	
	/**
	* create_time  Date(19)  //    
	*/
	public Date getCreateTime(){
		return createTime;
	}
	
	/**
	* create_time  Date(19)  //    
	*/
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
}