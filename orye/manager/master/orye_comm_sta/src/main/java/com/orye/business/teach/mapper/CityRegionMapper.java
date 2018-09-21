package com.orye.business.teach.mapper;

import java.util.List;
import java.util.Map;

import com.core.teamwork.base.mapper.BaseMapper;
import com.orye.business.teach.entity.CityRegion;

/**
 * @author cyl
 * @version 
 */
public interface CityRegionMapper extends BaseMapper<CityRegion>{
	
	public List<CityRegion> queryByParent(Map<String,Object> map);
	
}