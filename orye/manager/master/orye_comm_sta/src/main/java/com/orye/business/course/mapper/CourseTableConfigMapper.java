package com.orye.business.course.mapper;

import java.util.List;
import java.util.Map;

import com.core.teamwork.base.mapper.BaseMapper;
import com.orye.business.course.entity.CourseTableConfig;

/**
 * @author cyl
 * @version 
 */
public interface CourseTableConfigMapper extends BaseMapper<CourseTableConfig>{
	
	public Integer pageListCount(Map<String,Object> map);
	
	public List<CourseTableConfig> pageList(Map<String,Object> map);
	
	public void batchUpdate(Map<String,Object> map);
}