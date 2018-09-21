package com.orye.business.teach.mapper;

import java.util.List;
import java.util.Map;

import com.core.teamwork.base.mapper.BaseMapper;
import com.orye.business.teach.entity.TeachPlace;

/**
 * @author cyl
 * @version 
 */
public interface TeachPlaceMapper extends BaseMapper<TeachPlace>{
	public List<TeachPlace> getPageList(Map<String,Object> map);
	
	public Integer getPageListCout(Map<String,Object> map);
	
	public List<TeachPlace> getList(Map<String,Object> map);
	
	public TeachPlace queryDetail(Map<String,Object> map);
}