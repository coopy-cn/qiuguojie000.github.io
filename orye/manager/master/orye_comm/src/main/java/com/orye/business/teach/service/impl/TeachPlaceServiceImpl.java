package com.orye.business.teach.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.teach.entity.TeachPlace;
import com.orye.business.teach.mapper.TeachPlaceMapper;
import com.orye.business.teach.service.TeachPlaceService;
import com.core.teamwork.base.service.impl.BaseServiceImpl;
import com.core.teamwork.base.util.page.PageHelper;
import com.core.teamwork.base.util.page.PageObject;

/**
 * @author cyl
 * @version 
 */
@Service("teachPlaceService")
public class TeachPlaceServiceImpl extends BaseServiceImpl<TeachPlace, TeachPlaceMapper> implements TeachPlaceService {
	// 注入当前dao对象
    @Autowired
    private TeachPlaceMapper teachPlaceMapper;

    public TeachPlaceServiceImpl() {
        setMapperClass(TeachPlaceMapper.class, TeachPlace.class);
    }

    /**
	 * 后台站点列表
	 * @param map
	 * @return
	 */
	public PageObject<TeachPlace> getPageList(Map<String, Object> map) {
		
		int totalData = teachPlaceMapper.getPageListCout(map);
        PageHelper pageHelper = new PageHelper(totalData, map);
        List<TeachPlace> list = teachPlaceMapper.getPageList(pageHelper.getMap());
        PageObject<TeachPlace> pageObject = pageHelper.getPageObject();
        pageObject.setDataList(list);
		
		return pageObject;
	}
    
	/**
	 * （课程预约）-站点下拉框-根据市区查询站点（小程序接口）
	 * @param map
	 * @return
	 */
	public List<TeachPlace> getList(Map<String,Object> map){
		return teachPlaceMapper.getList(map);
	}
	
	/**
	 * （课程预约）-课程详情-根据id查询站点（小程序接口）
	 * @param map
	 * @return
	 */
	public TeachPlace queryDetail(Map<String,Object> map){
		return teachPlaceMapper.queryDetail(map);
	}
}
