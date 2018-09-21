package com.orye.business.teach.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.teach.entity.CityRegion;
import com.orye.business.teach.mapper.CityRegionMapper;
import com.orye.business.teach.service.CityRegionService;
import com.core.teamwork.base.service.impl.BaseServiceImpl;

/**
 * @author cyl
 * @version 
 */
@Service("cityRegionService")
public class CityRegionServiceImpl extends BaseServiceImpl<CityRegion, CityRegionMapper> implements CityRegionService {
	// 注入当前dao对象
    @Autowired
    private CityRegionMapper cityRegionMapper;

    public CityRegionServiceImpl() {
        setMapperClass(CityRegionMapper.class, CityRegion.class);
    }
    
    /**
     * （课程预约）-区下拉框-根据城市id查询区（小程序接口）
     */
    public List<CityRegion> queryByParent(Map<String,Object> map){
    	return cityRegionMapper.queryByParent(map);
    }
}
