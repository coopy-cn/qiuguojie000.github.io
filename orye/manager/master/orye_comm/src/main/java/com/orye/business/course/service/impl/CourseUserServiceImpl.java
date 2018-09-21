package com.orye.business.course.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.course.entity.CourseUser;
import com.orye.business.course.mapper.CourseUserMapper;
import com.orye.business.course.service.CourseUserService;
import com.core.teamwork.base.service.impl.BaseServiceImpl;

/**
 * @author cyl
 * @version 
 */
@Service("courseUserService")
public class CourseUserServiceImpl extends BaseServiceImpl<CourseUser, CourseUserMapper> implements CourseUserService {
	// 注入当前dao对象
    @Autowired
    private CourseUserMapper courseUserMapper;

    public CourseUserServiceImpl() {
        setMapperClass(CourseUserMapper.class, CourseUser.class);
    }
    
 
}
