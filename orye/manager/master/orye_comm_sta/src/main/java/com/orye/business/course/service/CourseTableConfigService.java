package com.orye.business.course.service;

import java.util.Map;

import com.core.teamwork.base.service.BaseService;
import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.course.entity.CourseTableConfig;
import com.orye.business.course.mapper.CourseTableConfigMapper;

/**
 * @author cyl
 * @version 
 */
public interface CourseTableConfigService extends BaseService<CourseTableConfig,CourseTableConfigMapper>  {
	/**
     * （课程预约）-课程列表（小程序接口）
     */
	public PageObject<CourseTableConfig> pageList(Map<String,Object> map);
	
	public void batchUpdate(Map<String,Object> map);
}
