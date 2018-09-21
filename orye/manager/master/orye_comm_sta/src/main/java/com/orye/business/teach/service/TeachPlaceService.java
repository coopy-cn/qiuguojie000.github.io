package com.orye.business.teach.service;

import java.util.List;
import java.util.Map;

import com.core.teamwork.base.service.BaseService;
import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.teach.entity.TeachPlace;
import com.orye.business.teach.mapper.TeachPlaceMapper;

/**
 * @author cyl
 * @version 
 */
public interface TeachPlaceService extends BaseService<TeachPlace,TeachPlaceMapper>  {
	
	/**
	 * 后台站点列表
	 * @param map
	 * @return
	 */
	public PageObject<TeachPlace> getPageList(Map<String,Object> map);
	
	/**
	 * （课程预约）-站点下拉框-根据市区查询站点（小程序接口）
	 * @param map
	 * @return
	 */
	public List<TeachPlace> getList(Map<String,Object> map);
	
	/**
	 * （课程预约）-课程详情-根据id查询站点（小程序接口）
	 * @param map
	 * @return
	 */
	public TeachPlace queryDetail(Map<String,Object> map);
}
