package com.orye.business.course.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.course_table_config      
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
place_id             Long(19)                               //站点id
teach_id             Long(19)                               //教师id
course_id            Long(19)                               //课程id
teach_date           Date(19)                               //上课时间
max_count            Integer(10)                 0          //最大人数
status               Integer(10)                 1          //状态，预留
is_delete            Integer(10)                 2          //是否删除1是2否
create_time          Date(19)                               //创建时间
*/
public class CourseTableConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	Long placeId;
	private	Long teachId;
	private	Long courseId;
	private	Date teachDate;
	private	Integer maxCount;
	private	Integer status;
	private	Integer isDelete;
	private	Date createTime;
	
	private String courseName;//课程名称
	private String teacherName;//老师姓名
	private String courseAddr;//站点名称
	private String teachTime;//上课时间String
	private String imgUrl;//课程图片
	private Integer buyCount;//预约人数
	private Integer amount;//单价
	private Integer expAmount;//体验价
	private Integer amountType;//1为体验价，2为单价
	private Integer courseUse;//消耗课时
	private String remark;//
	private Integer minYear;
	private Integer maxYear;
	private Integer minMonth;
	private Integer maxMonth;

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
	* place_id  Long(19)  //站点id    
	*/
	public Long getPlaceId(){
		return placeId;
	}
	
	/**
	* place_id  Long(19)  //站点id    
	*/
	public void setPlaceId(Long placeId){
		this.placeId = placeId;
	}
	
	/**
	* teach_id  Long(19)  //教师id    
	*/
	public Long getTeachId(){
		return teachId;
	}
	
	/**
	* teach_id  Long(19)  //教师id    
	*/
	public void setTeachId(Long teachId){
		this.teachId = teachId;
	}
	
	/**
	* course_id  Long(19)  //课程id    
	*/
	public Long getCourseId(){
		return courseId;
	}
	
	/**
	* course_id  Long(19)  //课程id    
	*/
	public void setCourseId(Long courseId){
		this.courseId = courseId;
	}
	
	/**
	* teach_date  Date(19)  //上课时间    
	*/
	public Date getTeachDate(){
		return teachDate;
	}
	
	/**
	* teach_date  Date(19)  //上课时间    
	*/
	public void setTeachDate(Date teachDate){
		this.teachDate = teachDate;
	}
	
	/**
	* max_count  Integer(10)  0  //最大人数    
	*/
	public Integer getMaxCount(){
		return maxCount;
	}
	
	/**
	* max_count  Integer(10)  0  //最大人数    
	*/
	public void setMaxCount(Integer maxCount){
		this.maxCount = maxCount;
	}
	
	/**
	* status  Integer(10)  //    
	*/
	public Integer getStatus(){
		return status;
	}
	
	/**
	* status  Integer(10)  //    
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getCourseAddr() {
		return courseAddr;
	}

	public void setCourseAddr(String courseAddr) {
		this.courseAddr = courseAddr;
	}

	public String getTeachTime() {
		return teachTime;
	}

	public void setTeachTime(String teachTime) {
		this.teachTime = teachTime;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getExpAmount() {
		return expAmount;
	}

	public void setExpAmount(Integer expAmount) {
		this.expAmount = expAmount;
	}

	public Integer getAmountType() {
		return amountType;
	}

	public void setAmountType(Integer amountType) {
		this.amountType = amountType;
	}

	public Integer getCourseUse() {
		return courseUse;
	}

	public void setCourseUse(Integer courseUse) {
		this.courseUse = courseUse;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMinYear() {
		return minYear;
	}

	public void setMinYear(Integer minYear) {
		this.minYear = minYear;
	}

	public Integer getMaxYear() {
		return maxYear;
	}

	public void setMaxYear(Integer maxYear) {
		this.maxYear = maxYear;
	}

	public Integer getMinMonth() {
		return minMonth;
	}

	public void setMinMonth(Integer minMonth) {
		this.minMonth = minMonth;
	}

	public Integer getMaxMonth() {
		return maxMonth;
	}

	public void setMaxMonth(Integer maxMonth) {
		this.maxMonth = maxMonth;
	}
	
}