package com.orye.manger.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.course.entity.CourseInfo;
import com.orye.business.course.entity.CourseOrder;
import com.orye.business.course.service.CourseInfoService;
import com.orye.business.course.service.CourseOrderService;
import com.orye.business.member.entity.MemberInfo;
import com.orye.business.member.entity.MemberUser;
import com.orye.business.member.service.MemberInfoService;
import com.orye.business.member.service.MemberUserService;
import com.orye.business.pay.entity.PayOrder;
import com.orye.business.pay.service.PayOrderService;
import com.orye.business.teach.entity.TeachPlace;
import com.orye.business.teach.entity.TeacherInfo;
import com.orye.business.teach.service.TeachPlaceService;
import com.orye.business.teach.service.TeacherInfoService;
import com.orye.manger.controller.course.CourseInfoController;

/**
 * @ClassName: CourseInfoController
 * @Description: 预约订单管理
 * @author qiuguojie
 * @date 2018年08月21日 上午10:46:10
 * 
 */
@RequestMapping("/courseOrder/*")
@Controller
public class CourseOrderController {
private static final Logger LOGGER = LoggerFactory.getLogger(CourseInfoController.class);
	
	@Autowired
	private CourseOrderService service;
	@Autowired
	private CourseInfoService courseInfoService;
	@Autowired
	private TeacherInfoService teacherInfoService;
	@Autowired
	private TeachPlaceService teachPlaceService;
	@Autowired
	private MemberUserService memberUserService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private PayOrderService payOrderService;
	
	/**
     * 
     * @author qiuguojie
     * @Title: list
     * @Description: 用户列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("order/course-order-list");
        PageObject<CourseOrder> list = service.adminPageList(map);
        
        Map<String,Object> param = new HashMap<>();
        List<CourseInfo> courseList = courseInfoService.query(param);
        List<TeacherInfo> teachList = teacherInfoService.query(param);
        List<TeachPlace> placeList = teachPlaceService.query(param);
        andView.addObject("courseList", courseList);
        andView.addObject("teachList", teachList);
        andView.addObject("placeList", placeList);
        andView.addObject("list", list);
        andView.addObject("map", map);
        return andView;
    }
    
    /**
     * 
     * @author qiuguojie
     * @Title: list
     * @Description: 详情
     */
    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("order/course-order-detail");
        
        CourseOrder obj = service.queryDetail(map);
        Map<String,Object> param = new HashMap<>();
        if(obj.getMemId()!=null){
        	param.put("memId", obj.getMemId());
        	param.put("openId", obj.getOpenId());
        	MemberUser memberUser = memberUserService.detail(param);
        	if(memberUser!=null){
        		param.clear();
        		param.put("id", memberUser.getMemId());
        		MemberInfo memberInfo = memberInfoService.detail(param);
        		andView.addObject("count", memberUser.getCount());
        		andView.addObject("memberInfo", memberInfo);
        	}
        }else{
        	param.put("orderId", obj.getOrderId());
        	PayOrder payOrder = payOrderService.detail(param);
        	andView.addObject("payOrder", payOrder);
        }
        
        andView.addObject("obj", obj);
        
        andView.addObject("map", map);
        return andView;
    }
}
