package com.orye.business.pay.mapper;

import java.util.List;
import java.util.Map;

import com.core.teamwork.base.mapper.BaseMapper;
import com.orye.business.pay.entity.PayOrder;

/**
 * @author cyl
 * @version 
 */
public interface PayOrderMapper extends BaseMapper<PayOrder>{
	public Integer getPageListCount(Map<String,Object> map);
	
	public List<PayOrder> getPageList(Map<String,Object> map);
}