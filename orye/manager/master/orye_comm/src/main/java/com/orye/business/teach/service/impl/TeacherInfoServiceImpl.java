package com.orye.business.teach.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.teach.entity.TeacherInfo;
import com.orye.business.teach.mapper.TeacherInfoMapper;
import com.orye.business.teach.service.TeacherInfoService;
import com.core.teamwork.base.service.impl.BaseServiceImpl;

/**
 * @author cyl
 * @version 
 */
@Service("teacherInfoService")
public class TeacherInfoServiceImpl extends BaseServiceImpl<TeacherInfo, TeacherInfoMapper> implements TeacherInfoService {
	// 注入当前dao对象
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    public TeacherInfoServiceImpl() {
        setMapperClass(TeacherInfoMapper.class, TeacherInfo.class);
    }
    
 
}
