package com.orye.business.teach.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.teach_place              
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
reg_id               Long(19)                               //市区id
course_addr          String(255)                            //站点名称
longitude            String(20)                             //经度
latitude             String(20)                             //纬度
place_url            String(255)                            //站点图片地址
remark               String(65535)                          //详情/简介
sort_num             Integer(10)                 0          //排序
status               Integer(10)                 1          //状态1.开启2.关闭
create_time          Date(19)                               //创建时间
*/
public class TeachPlace implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	Long regId;
	private	String courseAddr;
	private	String longitude;
	private	String latitude;
	private	String placeUrl;
	private	String remark;
	private	Integer sortNum;
	private	Integer status;
	private	Date createTime;
	
	private String regName;//区名称	南山区
	private String cityName;//市名称	深圳市
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
	* reg_id  Long(19)  //市区id    
	*/
	public Long getRegId(){
		return regId;
	}
	
	/**
	* reg_id  Long(19)  //市区id    
	*/
	public void setRegId(Long regId){
		this.regId = regId;
	}
	
	/**
	* course_addr  String(255)  //课程地址    
	*/
	public String getCourseAddr(){
		return courseAddr;
	}
	
	/**
	* course_addr  String(255)  //课程地址    
	*/
	public void setCourseAddr(String courseAddr){
		this.courseAddr = courseAddr;
	}
	
	/**
	* remark  String(65535)  //详情/简介    
	*/
	public String getRemark(){
		return remark;
	}
	
	/**
	* remark  String(65535)  //详情/简介    
	*/
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	* status  Integer(10)  1  //状态1.开启2.关闭    
	*/
	public Integer getStatus(){
		return status;
	}
	
	/**
	* status  Integer(10)  1  //状态1.开启2.关闭    
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

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPlaceUrl() {
		return placeUrl;
	}

	public void setPlaceUrl(String placeUrl) {
		this.placeUrl = placeUrl;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}