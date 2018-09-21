package com.orye.business.teach.service;

import java.util.List;
import java.util.Map;

import com.orye.business.teach.entity.CityRegion;
import com.orye.business.teach.mapper.CityRegionMapper;
import com.core.teamwork.base.service.BaseService;

/**
 * @author cyl
 * @version 
 */
public interface CityRegionService extends BaseService<CityRegion,CityRegionMapper>  {
	/**
     * （课程预约）-区下拉框-根据城市id查询区（小程序接口）
     */
	public List<CityRegion> queryByParent(Map<String,Object> map);
}
