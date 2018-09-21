package com.orye.manger.controller.course;

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
import com.orye.business.course.entity.CourseType;
import com.orye.business.course.service.CourseTypeService;
import com.orye.business.util.ParameterEunm;

/**
 * @ClassName: CourseTypeController
 * @Description: 课类管理
 * @author qiuguojie
 * @date 2018年08月21日 上午10:46:10
 * 
 */
@RequestMapping("/courseType/*")
@Controller
public class CourseTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseTypeController.class);
    @Autowired
    private CourseTypeService service;

    /**
     * 
     * @author qiuguojie
     * @Title: list
     * @Description: 课类列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("course/course-type-list");
        PageObject<CourseType> list = service.Pagequery(map);
        andView.addObject("list", list);
        return andView;
    }
    
    /**
     * 去新增页面
     * @param map
     * @return
     */
    @RequestMapping("/addToPage")
    public ModelAndView addToPage(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("course/course-type-add");
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
        	CourseType courseType = service.detail(map);
        	////判断是否名称是否存在
            if(courseType==null){
            	map.put("createTime", new Date());
            	service.add(map);
            	resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
            }else{
            	resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
            }
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
        ModelAndView andView = new ModelAndView("course/course-type-edit");
        CourseType obj = service.detail(map);
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
        	//判断是否更改名称
            if(map.get("oldName").toString().equals(map.get("name").toString())){
            	resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
            }else{
            	Map<String, Object> param = new HashMap<>();
            	param.put("name", map.get("name"));
            	CourseType courseType = service.detail(param);
            	//判断是否名称是否存在
                if(courseType==null){
                	service.update(map);
                	resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
                }else{
                	resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
        return resultMap;
    }
}
