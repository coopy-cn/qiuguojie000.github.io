package com.orye.business.course.service;

import java.util.Map;

import com.core.teamwork.base.service.BaseService;
import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.course.entity.CourseInfo;
import com.orye.business.course.entity.CourseOrder;
import com.orye.business.course.mapper.CourseOrderMapper;

/**
 * @author cyl
 * @version 
 */
public interface CourseOrderService extends BaseService<CourseOrder,CourseOrderMapper>  {
	/**
     * （课程预约）-立即预约-根据用户openid查询最后一次预约的信息（小程序接口）
     */
	public CourseOrder queryLast(Map<String,Object> map);
	/**
     * （我的）- 我的预约列表（小程序接口）
     */
	public PageObject<CourseOrder> pageList(Map<String,Object> map);
	/**
     * （我的）- 我的预约数量（小程序接口）
     */
	public Integer noTogoCount(Map<String,Object> map);
	/**
     * 后台预约订单列表
     */
	public PageObject<CourseOrder> adminPageList(Map<String,Object> map);
	/**
     * (课程预约)-课程详情（小程序接口）
     */
	public CourseOrder queryDetail(Map<String,Object> map);
	/**
     * (课程预约)-提交预约调用小程序支付（小程序接口）
     */
	public Map<String,String> submitOrder(Map<String,Object> map,CourseInfo courseInfo) throws Exception;
}
