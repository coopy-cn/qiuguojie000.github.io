package com.orye.business.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.course.entity.CourseOrder;
import com.orye.business.course.entity.CourseUser;
import com.orye.business.course.service.CourseOrderService;
import com.orye.business.course.service.CourseUserService;
import com.orye.business.member.entity.MemberInfo;
import com.orye.business.member.entity.MemberUser;
import com.orye.business.member.service.MemberInfoService;
import com.orye.business.member.service.MemberUserService;
import com.orye.business.pay.entity.PayOrder;
import com.orye.business.pay.mapper.PayOrderMapper;
import com.orye.business.pay.service.PayOrderService;
import com.orye.business.util.OrderUtil;
import com.orye.business.util.wxPay.WxPay;
import com.core.teamwork.base.service.impl.BaseServiceImpl;
import com.core.teamwork.base.util.date.DateUtil;
import com.core.teamwork.base.util.page.PageHelper;
import com.core.teamwork.base.util.page.PageObject;

/**
 * @author cyl
 * @version 
 */
@Service("payOrderService")
public class PayOrderServiceImpl extends BaseServiceImpl<PayOrder, PayOrderMapper> implements PayOrderService {
	// 注入当前dao对象
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
	private CourseOrderService courseOrderService;
	@Autowired
	private CourseUserService courseUserService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private MemberUserService memberUserService;

    public PayOrderServiceImpl() {
        setMapperClass(PayOrderMapper.class, PayOrder.class);
    }
    
    /**
     * 支付回调
     */
    public boolean callBack(CourseOrder order,PayOrder payorder,Map<String,String> map) throws Exception{
    	
    	
    	if(payorder.getStatus()==2){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date parse = sdf.parse(map.get("time_end"));
	    	
			//修改支付订单
	    	payorder.setStatus(1);
	    	payorder.setCreateTime(parse);
	    	payorder.setTransactionId(map.get("transaction_id"));
	    	this.update(payorder);
	    	
	    	if(payorder.getType()==1){
	    		Map<String,Object> param = new HashMap<>();
				param.put("openId", payorder.getOpenId());
				//查询用户
				CourseUser courseUser = courseUserService.detail(param);
				
				param.put("id", payorder.getMemId());
				//查询卡信息
				MemberInfo memberInfo = memberInfoService.detail(param);
				
				if(courseUser!=null&&memberInfo!=null){
					param.put("memId", payorder.getMemId());
					param.remove("id");
					//查询是否有这种卡
					MemberUser memberUser = memberUserService.detail(param);
					Date date = new Date();
					if(memberUser!=null){
						//已过期
						if(memberUser.getValidDate().getTime()<=date.getTime()){
							//重新修改有效期和次数（年卡不修改次数）
							memberUser.setValidDate(DateUtil.addMonth(date, memberInfo.getValidMonth()));
							if(memberInfo.getType()==2){
								memberUser.setCount(memberInfo.getCount());
							}
						}else{//未过期
							//叠加次数和有效期（年卡不叠加次数）
							memberUser.setValidDate(DateUtil.addMonth(memberUser.getValidDate(), memberInfo.getValidMonth()));
							if(memberInfo.getType()==2){
								memberUser.setCount(memberUser.getCount()+memberInfo.getCount());
							}
						}
						memberUserService.update(memberUser);
						
					}else{
						//购买新增卡
						param.clear();
						param.put("userId", courseUser.getId());
						param.put("openId", courseUser.getOpenId());
						param.put("memId", memberInfo.getId());
						param.put("count", memberInfo.getCount());
						if(memberInfo.getType()==1){
							param.put("count", 9999);
						}
						param.put("validDate", DateUtil.addMonth(date, memberInfo.getValidMonth()));
						param.put("createTime", new Date());
						memberUserService.add(param);
					}
				}else{
					return false;
				}
	    	}else{
	    		//修改预约订单
	    		order.setStatus(2);
	        	courseOrderService.update(order);
	    	}
    	}
    	return true;
    }
    
    //小程序购买接口
    public Map<String,String> buyMember(MemberInfo memberInfo,Map<String,Object> map){
    	PayOrder order = new PayOrder();
		order.setAmount(memberInfo.getAmount());
		order.setOpenId(map.get("sk").toString());
		order.setCreateTime(new Date());
		String orderId=OrderUtil.getOrder();
		order.setOrderId(orderId);
		order.setOrderName("购买"+memberInfo.getMemberName());
		order.setType(1);
		order.setMemId(memberInfo.getId());
		payOrderMapper.insertByEntity(order);
		
		Map<String,String> result = WxPay.xcxPay(map.get("sk").toString(), "购买"+memberInfo.getMemberName()
				, orderId, Double.valueOf(memberInfo.getAmount())/100+"", "192.168.1.77");
		
		return result;
		
    }
    
    public PageObject<PayOrder> getPageList(Map<String,Object> map){
    	int totalData = payOrderMapper.getPageListCount(map);
        PageHelper pageHelper = new PageHelper(totalData, map);
        List<PayOrder> list = payOrderMapper.getPageList(pageHelper.getMap());
        PageObject<PayOrder> pageObject = pageHelper.getPageObject();
        pageObject.setDataList(list);
        return pageObject;
    }
}
