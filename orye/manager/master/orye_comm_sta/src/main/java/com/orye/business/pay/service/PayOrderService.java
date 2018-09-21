package com.orye.business.pay.service;

import java.util.Map;

import com.core.teamwork.base.service.BaseService;
import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.course.entity.CourseOrder;
import com.orye.business.member.entity.MemberInfo;
import com.orye.business.pay.entity.PayOrder;
import com.orye.business.pay.mapper.PayOrderMapper;

/**
 * @author cyl
 * @version 
 */
public interface PayOrderService extends BaseService<PayOrder,PayOrderMapper>  {
	public boolean callBack(CourseOrder order,PayOrder payorder,Map<String,String> map) throws Exception;
	
	public Map<String,String> buyMember(MemberInfo memberInfo,Map<String,Object> map);
	
	public PageObject<PayOrder> getPageList(Map<String,Object> map);
}
