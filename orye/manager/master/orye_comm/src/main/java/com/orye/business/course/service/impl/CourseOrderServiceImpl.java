package com.orye.business.course.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.teamwork.base.service.impl.BaseServiceImpl;
import com.core.teamwork.base.util.date.DateUtil;
import com.core.teamwork.base.util.page.PageHelper;
import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.course.entity.CourseInfo;
import com.orye.business.course.entity.CourseOrder;
import com.orye.business.course.entity.CourseTableConfig;
import com.orye.business.course.mapper.CourseOrderMapper;
import com.orye.business.course.service.CourseOrderService;
import com.orye.business.course.service.CourseTableConfigService;
import com.orye.business.member.entity.MemberUser;
import com.orye.business.member.service.MemberUserService;
import com.orye.business.pay.entity.PayOrder;
import com.orye.business.pay.mapper.PayOrderMapper;
import com.orye.business.teach.entity.HomePerson;
import com.orye.business.teach.service.HomePersonService;
import com.orye.business.util.OrderUtil;
import com.orye.business.util.wxPay.WxPay;

/**
 * @author cyl
 * @version 
 */
@Service("courseOrderService")
public class CourseOrderServiceImpl extends BaseServiceImpl<CourseOrder, CourseOrderMapper> implements CourseOrderService {
	// 注入当前dao对象
    @Autowired
    private CourseOrderMapper courseOrderMapper;
    @Autowired
    private HomePersonService homePersonService;
    @Autowired
    private CourseTableConfigService courseTableConfigService;
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private MemberUserService memberUserService;
    
    public CourseOrderServiceImpl() {
        setMapperClass(CourseOrderMapper.class, CourseOrder.class);
    }
    
    /**
     * （课程预约）-立即预约-根据用户openid查询最后一次预约的信息（小程序接口）
     */
    public CourseOrder queryLast(Map<String,Object> map){
    	return courseOrderMapper.queryLast(map);
    }
    
    /**
     * （我的）- 我的预约列表（小程序接口）
     */
    public PageObject<CourseOrder> pageList(Map<String,Object> map){
    	map.put("status", "2");
    	int totalData = courseOrderMapper.pageListCount(map);
        PageHelper pageHelper = new PageHelper(totalData, map);
        List<CourseOrder> list = courseOrderMapper.pageList(pageHelper.getMap());
        PageObject<CourseOrder> pageObject = pageHelper.getPageObject();
        pageObject.setDataList(list);
        return pageObject;
    }
    
    /**
     * （我的）- 我的预约数量（小程序接口）
     */
    public Integer noTogoCount(Map<String,Object> map){
    	map.put("status", "2");
    	return courseOrderMapper.noTogoCount(map);
    }
    
    /**
     * 后台预约订单列表
     */
    public PageObject<CourseOrder> adminPageList(Map<String,Object> map){
    	int totalData = courseOrderMapper.adminPageListCount(map);
        PageHelper pageHelper = new PageHelper(totalData, map);
        List<CourseOrder> list = courseOrderMapper.adminPageList(pageHelper.getMap());
        PageObject<CourseOrder> pageObject = pageHelper.getPageObject();
        pageObject.setDataList(list);
        return pageObject;
    }
    
    /**
     * 订单详情
     */
    public CourseOrder queryDetail(Map<String,Object> map){
    	return courseOrderMapper.queryDetail(map);
    }
    
    /**
     * (课程预约)-提交预约调用小程序支付（小程序接口）
     */
    public Map<String,String> submitOrder(Map<String,Object> map,CourseInfo courseInfo) throws Exception{
    	Map<String,Object> param = new HashMap<>();
    	Date date = new Date();
		
		param.clear();
		param.put("id", map.get("conId"));
		CourseTableConfig courseTableConfig = courseTableConfigService.detail(param);
		
		param.clear();
		param.put("openId", map.get("sk"));
		param.put("status", 2);
		CourseOrder obj = this.queryLast(param);
		Integer amount = courseInfo.getExpAmount();
		if(obj!=null){
			amount=courseInfo.getAmount();
		}
		
		String orderId=OrderUtil.getOrder();
		Map<String,String> result = new HashMap<>();
		boolean con = false;
		//判断是选择会员卡预约还是直接支付
		if(map.containsKey("memId")){
			param.clear();
			param.put("memId", map.get("memId"));
			param.put("openId", map.get("sk"));
			MemberUser memberUser = memberUserService.detail(param);
			if(memberUser!=null
					&&memberUser.getCount()>=courseInfo.getCourseUse()//判断剩余次数是否够用
					&&memberUser.getValidDate().getTime()>date.getTime()){//判断是否在有效期
				map.put("status", 2);
				map.put("memId", memberUser.getMemId());
				
				if(memberUser.getCount()!=9999){
					//修改剩余次数
					memberUser.setCount(memberUser.getCount()-courseInfo.getCourseUse());
				}
				memberUserService.update(memberUser);
				result.put("code", "10000");
			}else{
				result.put("code", "10001");
				return result;
			}
		}else{
			con = true;
			PayOrder order = new PayOrder();
			order.setAmount(amount);
			order.setOpenId(map.get("sk").toString());
			order.setCreateTime(date);
			order.setOrderId(orderId);
			order.setOrderName("预约课程");
			order.setType(2);
			payOrderMapper.insertByEntity(order);
			
			map.put("orderId", orderId);
		}
		
		//添加预约订单
		map.put("openId", map.get("sk"));
		map.put("createTime", date);
		map.put("placeId", courseTableConfig.getPlaceId());
		map.put("teachId", courseTableConfig.getTeachId());
		map.put("courseId", courseTableConfig.getCourseId());
		map.put("amount", amount);
		this.add(map);
		
		//去支付
		if(con){
			result = WxPay.xcxPay(map.get("sk").toString(), "课程预约"
					, orderId, Double.valueOf(amount)/100+"", "192.168.1.77");
		}
		return result;
    }
}
