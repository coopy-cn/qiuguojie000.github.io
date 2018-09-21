package com.orye.business.course.mapper;

import java.util.Map;

import com.core.teamwork.base.mapper.BaseMapper;
import com.orye.business.course.entity.CourseInfo;

/**
 * @author cyl
 * @version 
 */
public interface CourseInfoMapper extends BaseMapper<CourseInfo>{
	public CourseInfo queryDetail(Map<String,Object> map);
}