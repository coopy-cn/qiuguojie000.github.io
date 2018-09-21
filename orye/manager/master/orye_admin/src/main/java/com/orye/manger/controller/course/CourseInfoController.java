package com.orye.manger.controller.course;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.orye.business.course.entity.CourseInfo;
import com.orye.business.course.entity.CourseType;
import com.orye.business.course.service.CourseInfoService;
import com.orye.business.course.service.CourseTypeService;
import com.orye.business.util.ParameterEunm;

/**
 * @ClassName: CourseInfoController
 * @Description: 课程信息相关
 * @author qiuguojie
 * @date 2018年08月21日 上午10:46:10
 * 
 */
@RequestMapping("/courseInfo/*")
@Controller
public class CourseInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseInfoController.class);
    @Autowired
    private CourseInfoService service;
    
    @Autowired
    private CourseTypeService courseTypeService;

    /**
     * 
     * @author qiuguojie
     * @Title: list
     * @Description: 课程列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("course/course-info-list");
        PageObject<CourseInfo> list = service.Pagequery(map);
        Map<String, Object> param = new HashMap<>();
        List<CourseType> typeList = courseTypeService.query(param);
        andView.addObject("typeList", typeList);
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
        ModelAndView andView = new ModelAndView("course/course-info-add");
        Map<String, Object> param = new HashMap<>();
        List<CourseType> typeList = courseTypeService.query(param);
        andView.addObject("typeList", typeList);
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
        	double expAmount = Double.valueOf(map.get("amount").toString());
        	map.put("createTime", new Date());
        	map.put("amount", (int)(amount*100));
        	map.put("expAmount", (int)(expAmount*100));
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
        ModelAndView andView = new ModelAndView("course/course-info-edit");
        CourseInfo obj = service.detail(map);
        Map<String, Object> param = new HashMap<>();
        List<CourseType> typeList = courseTypeService.query(param);
        andView.addObject("typeList", typeList);
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
        	double expAmount = Double.valueOf(map.get("amount").toString());
        	map.put("amount", (int)(amount*100));
        	map.put("expAmount", (int)(expAmount*100));
        	service.update(map);
        	resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
        return resultMap;
    }
    
}
