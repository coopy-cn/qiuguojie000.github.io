package com.orye.manger.controller.member;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.core.teamwork.base.util.page.PageObject;
import com.core.teamwork.base.util.returnback.ReMessage;
import com.orye.business.member.entity.MemberInfo;
import com.orye.business.member.service.MemberInfoService;
import com.orye.business.util.ParameterEunm;
import com.orye.manger.controller.course.CourseInfoController;

@RequestMapping("/memberInfo/*")
@Controller
public class MemberInfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseInfoController.class);
	
	@Autowired
	private MemberInfoService service;
	/**
     * 
     * @author qiuguojie
     * @Title: list
     * @Description: 课程列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("member/member-info-list");
        PageObject<MemberInfo> list = service.Pagequery(map);
        andView.addObject("list", list);
        andView.addObject("map", map);
        return andView;
    }
    
    /**
     * 去新增页面
     * @param map
     * @return
     */
    @RequestMapping("/addToPage")
    public ModelAndView addToPage(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("member/member-info-add");
        
        return andView;
    }
    
    /**
     * 新增
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public Map<String, Object> add(@RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	double amount = Double.valueOf(map.get("amount").toString());
        	map.put("createTime", new Date());
        	map.put("amount", (int)(amount*100));
        	service.add(map);
        	resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
        return resultMap;
    }
    
    /**
     * 去修改页面
     * @param map
     * @return
     */
    @RequestMapping("/editToPage")
    public ModelAndView editToPage(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("member/member-info-edit");
        MemberInfo obj = service.detail(map);
        andView.addObject("obj", obj);
        return andView;
    }
    
    /**
     * 修改
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public Map<String, Object> edit(@RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	double amount = Double.valueOf(map.get("amount").toString());
        	map.put("amount", (int)(amount*100));
        	if("1".equals(map.get("type").toString())){
        		map.put("count", 0);
        	}
        	service.update(map);
        	resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
        return resultMap;
    }
}
