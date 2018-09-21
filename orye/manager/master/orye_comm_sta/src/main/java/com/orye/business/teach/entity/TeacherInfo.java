package com.orye.business.teach.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.teacher_info             
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
name                 String(100)                            //姓名
photo_url            String(255)                            //相片
phone                String(20)                             //手机号码
card_id              String(20)                             //身份证号
remark               String(65535)                          //简介
teach_type           Integer(10)                 1          //教学类型1.全职2.兼职
job                  String(50)                             //现任职职位
company              String(255)                            //现任职单位
teach_amout          Integer(10)                 0          //课时费（单次课），单位为分
sort_num             Integer(10)                 0          //排序
status               Integer(10)                 1          //状态1.正常2.禁用
create_time          Date(19)                               //创建时间
*/
public class TeacherInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	String name;
	private	String photoUrl;
	private	String phone;
	private	String cardId;
	private	String remark;
	private	Integer teachType;
	private	String job;
	private	String company;
	private	Integer teachAmout;
	private	Integer sortNum;
	private	Integer status;
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
	* name  String(100)  //姓名    
	*/
	public String getName(){
		return name;
	}
	
	/**
	* name  String(100)  //姓名    
	*/
	public void setName(String name){
		this.name = name;
	}
	
	/**
	* phone  String(20)  //手机号码    
	*/
	public String getPhone(){
		return phone;
	}
	
	/**
	* phone  String(20)  //手机号码    
	*/
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	/**
	* card_id  String(20)  //身份证号    
	*/
	public String getCardId(){
		return cardId;
	}
	
	/**
	* card_id  String(20)  //身份证号    
	*/
	public void setCardId(String cardId){
		this.cardId = cardId;
	}
	
	/**
	* remark  String(65535)  //简介    
	*/
	public String getRemark(){
		return remark;
	}
	
	/**
	* remark  String(65535)  //简介    
	*/
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	* teach_type  Integer(10)  1  //教学类型1.全职2.兼职    
	*/
	public Integer getTeachType(){
		return teachType;
	}
	
	/**
	* teach_type  Integer(10)  1  //教学类型1.全职2.兼职    
	*/
	public void setTeachType(Integer teachType){
		this.teachType = teachType;
	}
	
	/**
	* job  String(50)  //现任职职位    
	*/
	public String getJob(){
		return job;
	}
	
	/**
	* job  String(50)  //现任职职位    
	*/
	public void setJob(String job){
		this.job = job;
	}
	
	/**
	* company  String(255)  //现任职单位    
	*/
	public String getCompany(){
		return company;
	}
	
	/**
	* company  String(255)  //现任职单位    
	*/
	public void setCompany(String company){
		this.company = company;
	}
	
	/**
	* teach_amout  Integer(10)  0  //课时费（单次课），单位为分    
	*/
	public Integer getTeachAmout(){
		return teachAmout;
	}
	
	/**
	* teach_amout  Integer(10)  0  //课时费（单次课），单位为分    
	*/
	public void setTeachAmout(Integer teachAmout){
		this.teachAmout = teachAmout;
	}
	
	/**
	* status  Integer(10)  1  //状态1.正常2.禁用    
	*/
	public Integer getStatus(){
		return status;
	}
	
	/**
	* status  Integer(10)  1  //状态1.正常2.禁用    
	*/
	public void setStatus(Integer status){
		this.status = status;
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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	
}