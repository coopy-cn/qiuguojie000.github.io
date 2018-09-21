package com.orye.business.teach.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.teach.entity.HomePerson;
import com.orye.business.teach.mapper.HomePersonMapper;
import com.orye.business.teach.service.HomePersonService;
import com.core.teamwork.base.service.impl.BaseServiceImpl;

/**
 * @author cyl
 * @version 
 */
@Service("homePersonService")
public class HomePersonServiceImpl extends BaseServiceImpl<HomePerson, HomePersonMapper> implements HomePersonService {
	// 注入当前dao对象
    @Autowired
    private HomePersonMapper homePersonMapper;

    public HomePersonServiceImpl() {
        setMapperClass(HomePersonMapper.class, HomePerson.class);
    }
    
 
}
