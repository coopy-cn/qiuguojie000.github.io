package com.orye.manger.controller.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.teamwork.base.util.ObjectUtil;
import com.core.teamwork.base.util.ReadProChange;
import com.core.teamwork.base.util.page.PageObject;
import com.core.teamwork.base.util.returnback.ReMessage;
import com.orye.business.course.entity.CourseUser;
import com.orye.business.course.service.CourseUserService;
import com.orye.business.member.entity.MemberInfo;
import com.orye.business.member.service.MemberInfoService;
import com.orye.business.member.service.MemberUserService;
import com.orye.business.pay.service.PayOrderService;
import com.orye.business.util.ParameterEunm;

@Controller
@RequestMapping("/api/*")
public class UserMemberController {
	private static final Log logger = LogFactory.getLog(CourseController.class);
	
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private MemberUserService memberUserService;
	@Autowired
	private CourseUserService courseUserService;
	@Autowired
	private PayOrderService payOrderService;
	
	@ResponseBody
	@RequestMapping(value = "/memberList")
	public Map<String,Object> memberList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			boolean con = ObjectUtil.checkObject(new String[] { "sk"}, map);
			if(con){
				map.put("sortName", "count");
				map.put("openId", map.get("sk"));
				PageObject<MemberInfo> list =  memberInfoService.getPage(map);
				resultMap.put("list", list);
				resultMap.put("phone", ReadProChange.getValue("service_phone"));
				resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
			}else{
				resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"缺少参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/buyMember")
	public Map<String,Object> buyMember(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			boolean con = ObjectUtil.checkObject(new String[] { "sk","memId"}, map);
			if(con){
				Map<String,Object> param = new HashMap<>();
				param.put("openId", map.get("sk"));
				//查询用户
				CourseUser courseUser = courseUserService.detail(param);
				
				param.put("id", map.get("memId"));
				//查询卡信息
				MemberInfo memberInfo = memberInfoService.detail(param);
				
				if(courseUser!=null&&memberInfo!=null){
					Map<String,String> result = payOrderService.buyMember(memberInfo, map);
					if("10000".equals(result.get("code"))){
						resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, result);
					}else{
						resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
					}
				}else{
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"缺少参数");
				}
			}else{
				resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"缺少参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/myMember")
	public Map<String,Object> myMember(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			boolean con = ObjectUtil.checkObject(new String[] { "sk"}, map);
			if(con){
				map.put("openId", map.get("sk"));
				List<MemberInfo> list = memberInfoService.queryMyMember(map);
				resultMap.put("list", list);
				resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
			}else{
				resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"缺少参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		
		return resultMap;
	}
}
