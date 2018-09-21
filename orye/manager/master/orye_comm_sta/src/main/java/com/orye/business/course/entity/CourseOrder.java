package com.orye.business.course.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.course_order             
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
con_id               Long(19)                               //课程表配置id
place_id             Long(19)                               //站点id
teach_id             Long(19)                               //教师id
course_id            Long(19)                               //课程id
mem_id               Long(19)                               //卡id,关联表member_info(使用卡预约才有值)
order_id             String(64)                             //支付订单号
amount               Integer(10)                 0          //支付金额，单位分
fat_name             String(100)                            //父亲姓名
mot_name             String(100)                            //母亲姓名
fat_phone            String(20)                             //父亲联系方式
mot_phone            String(20)                             //母亲联系方式
email                String(50)                             //邮箱
address              String(255)                            //地址
chirld_name          String(100)                            //孩子姓名
sex                  Integer(10)                 1          //性别1男.2女
birth_date           Date(19)                               //出生年月日
open_id              String(100)                            //微信openid
status               Integer(10)                 1          //状态1.锁定2.预约成功
create_time          Date(19)                               //创建时间
lock_time            Date(19)                               //解锁时间
*/
public class CourseOrder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	Long conId;
	private	Long placeId;
	private	Long teachId;
	private	Long courseId;
	private	Long memId;
	private	String orderId;
	private	Integer amount;
	private	String fatName;
	private	String motName;
	private	String fatPhone;
	private	String motPhone;
	private	String email;
	private	String address;
	private	String chirldName;
	private	Integer sex;
	private	Date birthDate;
	private	String openId;
	private	Integer status;
	private	Date createTime;
	private	Date lockTime;
	
	private	String strBirthDate;
	private String courseName;//课程名称
	private String teacherName;//老师姓名
	private String courseAddr;//站点名称
	private String teachTime;//上课时间String
	private Date teachDate;//Date
	private String regName;//区名称
	private String cityName;//城市名
	private String typeName;//课类名
	private String imgUrl;
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
	* con_id  Long(19)  //课程表配置id    
	*/
	public Long getConId(){
		return conId;
	}
	
	/**
	* con_id  Long(19)  //课程表配置id    
	*/
	public void setConId(Long conId){
		this.conId = conId;
	}
	
	/**
	* fat_name  String(100)  //父亲姓名    
	*/
	public String getFatName(){
		return fatName;
	}
	
	/**
	* fat_name  String(100)  //父亲姓名    
	*/
	public void setFatName(String fatName){
		this.fatName = fatName;
	}
	
	/**
	* mot_name  String(100)  //母亲姓名    
	*/
	public String getMotName(){
		return motName;
	}
	
	/**
	* mot_name  String(100)  //母亲姓名    
	*/
	public void setMotName(String motName){
		this.motName = motName;
	}
	
	/**
	* fat_phone  String(20)  //父亲联系方式    
	*/
	public String getFatPhone(){
		return fatPhone;
	}
	
	/**
	* fat_phone  String(20)  //父亲联系方式    
	*/
	public void setFatPhone(String fatPhone){
		this.fatPhone = fatPhone;
	}
	
	/**
	* mot_phone  String(20)  //母亲联系方式    
	*/
	public String getMotPhone(){
		return motPhone;
	}
	
	/**
	* mot_phone  String(20)  //母亲联系方式    
	*/
	public void setMotPhone(String motPhone){
		this.motPhone = motPhone;
	}
	
	/**
	* email  String(50)  //邮箱    
	*/
	public String getEmail(){
		return email;
	}
	
	/**
	* email  String(50)  //邮箱    
	*/
	public void setEmail(String email){
		this.email = email;
	}
	
	/**
	* address  String(255)  //地址    
	*/
	public String getAddress(){
		return address;
	}
	
	/**
	* address  String(255)  //地址    
	*/
	public void setAddress(String address){
		this.address = address;
	}
	
	/**
	* chirld_name  String(100)  //孩子姓名    
	*/
	public String getChirldName(){
		return chirldName;
	}
	
	/**
	* chirld_name  String(100)  //孩子姓名    
	*/
	public void setChirldName(String chirldName){
		this.chirldName = chirldName;
	}
	
	/**
	* sex  Integer(10)  1  //性别1男.2女    
	*/
	public Integer getSex(){
		return sex;
	}
	
	/**
	* sex  Integer(10)  1  //性别1男.2女    
	*/
	public void setSex(Integer sex){
		this.sex = sex;
	}
	
	/**
	* birth_date  Date(19)  //出生年月日    
	*/
	public Date getBirthDate(){
		return birthDate;
	}
	
	/**
	* birth_date  Date(19)  //出生年月日    
	*/
	public void setBirthDate(Date birthDate){
		this.birthDate = birthDate;
	}
	
	/**
	* open_id  String(100)  //微信openid    
	*/
	public String getOpenId(){
		return openId;
	}
	
	/**
	* open_id  String(100)  //微信openid    
	*/
	public void setOpenId(String openId){
		this.openId = openId;
	}
	
	/**
	* status  Integer(10)  1  //状态1.锁定2.预约成功    
	*/
	public Integer getStatus(){
		return status;
	}
	
	/**
	* status  Integer(10)  1  //状态1.锁定2.预约成功    
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
	
	/**
	* lock_time  Date(19)  //解锁时间    
	*/
	public Date getLockTime(){
		return lockTime;
	}
	
	/**
	* lock_time  Date(19)  //解锁时间    
	*/
	public void setLockTime(Date lockTime){
		this.lockTime = lockTime;
	}

	public String getStrBirthDate() {
		return strBirthDate;
	}

	public void setStrBirthDate(String strBirthDate) {
		this.strBirthDate = strBirthDate;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public Long getTeachId() {
		return teachId;
	}

	public void setTeachId(Long teachId) {
		this.teachId = teachId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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

	public Date getTeachDate() {
		return teachDate;
	}

	public void setTeachDate(Date teachDate) {
		this.teachDate = teachDate;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Long getMemId() {
		return memId;
	}

	public void setMemId(Long memId) {
		this.memId = memId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
}