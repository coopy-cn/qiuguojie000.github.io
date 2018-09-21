package com.orye.business.teach.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.city_region              
--------------------------------------------------------
id                   Long(19)           NOTNULL             //
parent_id            Long(19)                               //
name                 String(50)                             //
*/
public class CityRegion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long id;
	private	Long parentId;
	private	String name;

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
	* parent_id  Long(19)  //    
	*/
	public Long getParentId(){
		return parentId;
	}
	
	/**
	* parent_id  Long(19)  //    
	*/
	public void setParentId(Long parentId){
		this.parentId = parentId;
	}
	
	/**
	* name  String(50)  //    
	*/
	public String getName(){
		return name;
	}
	
	/**
	* name  String(50)  //    
	*/
	public void setName(String name){
		this.name = name;
	}
	
}