package com.orye.business.course.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.course_user              
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
open_id              String(64)                             //微信openid（唯一）
name                 String(50)                             //姓名
nick_name            String(50)                             //用户昵称（微信获取）
avatar_url           String(255)                            //用户头像（微信获取）
sex                  Integer(10)                 1          //1.男2.女（微信获取）
province             String(20)                             //省份（微信获取）
country              String(40)                             //国家（微信获取）
city                 String(20)                             //城市（微信获取）
create_time          Date(19)                               //创建时间
*/
public class CourseUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	String openId;
	private	String name;
	private	String nickName;
	private	String avatarUrl;
	private	Integer sex;
	private	String province;
	private	String country;
	private	String city;
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
	* open_id  String(64)  //微信openid（唯一）    
	*/
	public String getOpenId(){
		return openId;
	}
	
	/**
	* open_id  String(64)  //微信openid（唯一）    
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
	* nick_name  String(50)  //用户昵称（微信获取）    
	*/
	public String getNickName(){
		return nickName;
	}
	
	/**
	* nick_name  String(50)  //用户昵称（微信获取）    
	*/
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	
	/**
	* avatar_url  String(255)  //用户头像（微信获取）    
	*/
	public String getAvatarUrl(){
		return avatarUrl;
	}
	
	/**
	* avatar_url  String(255)  //用户头像（微信获取）    
	*/
	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl = avatarUrl;
	}
	
	/**
	* sex  Integer(10)  1  //1.男2.女（微信获取）    
	*/
	public Integer getSex(){
		return sex;
	}
	
	/**
	* sex  Integer(10)  1  //1.男2.女（微信获取）    
	*/
	public void setSex(Integer sex){
		this.sex = sex;
	}
	
	/**
	* province  String(20)  //省份（微信获取）    
	*/
	public String getProvince(){
		return province;
	}
	
	/**
	* province  String(20)  //省份（微信获取）    
	*/
	public void setProvince(String province){
		this.province = province;
	}
	
	/**
	* country  String(40)  //国家（微信获取）    
	*/
	public String getCountry(){
		return country;
	}
	
	/**
	* country  String(40)  //国家（微信获取）    
	*/
	public void setCountry(String country){
		this.country = country;
	}
	
	/**
	* city  String(20)  //城市（微信获取）    
	*/
	public String getCity(){
		return city;
	}
	
	/**
	* city  String(20)  //城市（微信获取）    
	*/
	public void setCity(String city){
		this.city = city;
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
	
}