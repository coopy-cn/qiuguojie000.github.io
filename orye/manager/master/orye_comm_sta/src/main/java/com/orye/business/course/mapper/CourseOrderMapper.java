package com.orye.business.course.mapper;

import java.util.List;
import java.util.Map;

import com.core.teamwork.base.mapper.BaseMapper;
import com.orye.business.course.entity.CourseOrder;

/**
 * @author cyl
 * @version 
 */
public interface CourseOrderMapper extends BaseMapper<CourseOrder>{
	public CourseOrder queryLast(Map<String,Object> map);
	
	public List<CourseOrder> pageList(Map<String,Object> map);
	
	public Integer pageListCount(Map<String,Object> map);
	
	public Integer noTogoCount(Map<String,Object> map);
	
	public List<CourseOrder> adminPageList(Map<String,Object> map);
	
	public Integer adminPageListCount(Map<String,Object> map);
	
	public CourseOrder queryDetail(Map<String,Object> map);
}