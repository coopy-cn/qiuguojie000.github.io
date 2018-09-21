package com.orye.business.course.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.course.entity.CourseInfo;
import com.orye.business.course.mapper.CourseInfoMapper;
import com.orye.business.course.service.CourseInfoService;
import com.core.teamwork.base.service.impl.BaseServiceImpl;

/**
 * @author cyl
 * @version 
 */
@Service("courseInfoService")
public class CourseInfoServiceImpl extends BaseServiceImpl<CourseInfo, CourseInfoMapper> implements CourseInfoService {
	// 注入当前dao对象
    @Autowired
    private CourseInfoMapper courseInfoMapper;

    public CourseInfoServiceImpl() {
        setMapperClass(CourseInfoMapper.class, CourseInfo.class);
    }
    
    /**
     * 根据配置id查询课程详情
     */
    public CourseInfo queryDetail(Map<String,Object> map){
    	return courseInfoMapper.queryDetail(map);
    }
}
