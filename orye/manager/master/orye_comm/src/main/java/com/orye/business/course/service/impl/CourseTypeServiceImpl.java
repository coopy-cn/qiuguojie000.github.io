package com.orye.business.course.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.course.entity.CourseType;
import com.orye.business.course.mapper.CourseTypeMapper;
import com.orye.business.course.service.CourseTypeService;
import com.core.teamwork.base.service.impl.BaseServiceImpl;

/**
 * @author cyl
 * @version 
 */
@Service("courseTypeService")
public class CourseTypeServiceImpl extends BaseServiceImpl<CourseType, CourseTypeMapper> implements CourseTypeService {
	// 注入当前dao对象
    @Autowired
    private CourseTypeMapper courseTypeMapper;

    public CourseTypeServiceImpl() {
        setMapperClass(CourseTypeMapper.class, CourseType.class);
    }
    
 
}
