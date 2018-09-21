package com.orye.business.course.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.course_info              
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
type_id              Long(19)                               //课类id
course_name          String(100)                            //课程名称
img_url              String(255)                            //图片地址
img_urls             String(65535)                          //轮播图多张
textImg              String(255)                            //文章图片
remark               String(65535)                          //课程简介
amount               Integer(10)                 0          //单价（分）
exp_amount           Integer(10)                 0          //体验价（分）
max_count            Integer(10)                 0          //最大上课人数
min_year             Integer(10)                 0          //适用年龄最小年岁
min_month            Integer(10)                 0          //适用年龄最小月数
max_year             Integer(10)                 0          //适用年龄最大年岁
max_month            Integer(10)                 0          //适用年龄最大月数
course_time_len      Integer(10)                 0          //课程时长（分钟）
course_use           Integer(10)                 0          //消耗课时
sort_num             Integer(10)                 0          //排序
status               Integer(10)                 1          //状态1.开启2.关闭
is_delete            Integer(10)                 2          //是否删除1.是2.否
create_time          Date(19)                               //创建时间
*/
public class CourseInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	Long typeId;
	private	String courseName;
	private	String imgUrl;
	private	String imgUrls;
	private	String textImg;
	private	String remark;
	private	Integer amount;
	private	Integer expAmount;
	private	Integer maxCount;
	private	Integer minYear;
	private	Integer minMonth;
	private	Integer maxYear;
	private	Integer maxMonth;
	private	Integer courseTimeLen;
	private	Integer courseUse;
	private	Integer sortNum;
	private	Integer status;
	private	Integer isDelete;
	private	Date createTime;


	private String name;
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
	* type_id  Long(19)  //课类id    
	*/
	public Long getTypeId(){
		return typeId;
	}
	
	/**
	* type_id  Long(19)  //课类id    
	*/
	public void setTypeId(Long typeId){
		this.typeId = typeId;
	}
	
	/**
	* course_name  String(100)  //课程名称    
	*/
	public String getCourseName(){
		return courseName;
	}
	
	/**
	* course_name  String(100)  //课程名称    
	*/
	public void setCourseName(String courseName){
		this.courseName = courseName;
	}
	
	/**
	* remark  String(65535)  //课程简介    
	*/
	public String getRemark(){
		return remark;
	}
	
	/**
	* remark  String(65535)  //课程简介    
	*/
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	* max_count  Integer(10)  0  //最大上课人数    
	*/
	public Integer getMaxCount(){
		return maxCount;
	}
	
	/**
	* max_count  Integer(10)  0  //最大上课人数    
	*/
	public void setMaxCount(Integer maxCount){
		this.maxCount = maxCount;
	}
	
	/**
	* min_year  Integer(10)  0  //适用年龄最小年岁    
	*/
	public Integer getMinYear(){
		return minYear;
	}
	
	/**
	* min_year  Integer(10)  0  //适用年龄最小年岁    
	*/
	public void setMinYear(Integer minYear){
		this.minYear = minYear;
	}
	
	/**
	* min_month  Integer(10)  0  //适用年龄最小月数    
	*/
	public Integer getMinMonth(){
		return minMonth;
	}
	
	/**
	* min_month  Integer(10)  0  //适用年龄最小月数    
	*/
	public void setMinMonth(Integer minMonth){
		this.minMonth = minMonth;
	}
	
	/**
	* max_year  Integer(10)  0  //适用年龄最大年岁    
	*/
	public Integer getMaxYear(){
		return maxYear;
	}
	
	/**
	* max_year  Integer(10)  0  //适用年龄最大年岁    
	*/
	public void setMaxYear(Integer maxYear){
		this.maxYear = maxYear;
	}
	
	/**
	* max_month  Integer(10)  0  //适用年龄最大月数    
	*/
	public Integer getMaxMonth(){
		return maxMonth;
	}
	
	/**
	* max_month  Integer(10)  0  //适用年龄最大月数    
	*/
	public void setMaxMonth(Integer maxMonth){
		this.maxMonth = maxMonth;
	}
	
	/**
	* course_time_len  Integer(10)  0  //课程时长（分钟）    
	*/
	public Integer getCourseTimeLen(){
		return courseTimeLen;
	}
	
	/**
	* course_time_len  Integer(10)  0  //课程时长（分钟）    
	*/
	public void setCourseTimeLen(Integer courseTimeLen){
		this.courseTimeLen = courseTimeLen;
	}
	
	/**
	* course_use  Integer(10)  0  //消耗课时    
	*/
	public Integer getCourseUse(){
		return courseUse;
	}
	
	/**
	* course_use  Integer(10)  0  //消耗课时    
	*/
	public void setCourseUse(Integer courseUse){
		this.courseUse = courseUse;
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
	* is_delete  Integer(10)  2  //是否删除1.是2.否    
	*/
	public Integer getIsDelete(){
		return isDelete;
	}
	
	/**
	* is_delete  Integer(10)  2  //是否删除1.是2.否    
	*/
	public void setIsDelete(Integer isDelete){
		this.isDelete = isDelete;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}

	public String getTextImg() {
		return textImg;
	}

	public void setTextImg(String textImg) {
		this.textImg = textImg;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getExpAmount() {
		return expAmount;
	}

	public void setExpAmount(Integer expAmount) {
		this.expAmount = expAmount;
	}
	
}