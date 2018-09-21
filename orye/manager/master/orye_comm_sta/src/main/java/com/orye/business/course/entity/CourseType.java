package com.orye.business.course.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.course_type              
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
name                 String(50)                             //课程类型名称
create_time          Date(19)                               //创建时间
*/
public class CourseType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	String name;
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
	* name  String(50)  //课程类型名称    
	*/
	public String getName(){
		return name;
	}
	
	/**
	* name  String(50)  //课程类型名称    
	*/
	public void setName(String name){
		this.name = name;
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