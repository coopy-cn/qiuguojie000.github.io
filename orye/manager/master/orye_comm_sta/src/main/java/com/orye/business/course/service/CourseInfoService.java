package com.orye.business.course.service;

import java.util.Map;

import com.orye.business.course.entity.CourseInfo;
import com.orye.business.course.mapper.CourseInfoMapper;
import com.core.teamwork.base.service.BaseService;

/**
 * @author cyl
 * @version 
 */
public interface CourseInfoService extends BaseService<CourseInfo,CourseInfoMapper>  {
	/**
     * 根据配置id查询课程详情
     */
	public CourseInfo queryDetail(Map<String,Object> map);
}
