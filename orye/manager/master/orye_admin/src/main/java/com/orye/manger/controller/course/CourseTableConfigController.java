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
import com.orye.business.course.entity.CourseTableConfig;
import com.orye.business.course.service.CourseInfoService;
import com.orye.business.course.service.CourseTableConfigService;
import com.orye.business.teach.entity.TeachPlace;
import com.orye.business.teach.entity.TeacherInfo;
import com.orye.business.teach.service.TeachPlaceService;
import com.orye.business.teach.service.TeacherInfoService;
import com.orye.business.util.ParameterEunm;

/**
 * @ClassName: courseTableConfig
 * @Description: 课程安排相关
 * @author qiuguojie
 * @date 2018年08月21日 上午10:46:10
 * 
 */
@RequestMapping("/courseTableConfig/*")
@Controller
public class CourseTableConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseTableConfigController.class);
    @Autowired
    private CourseTableConfigService service;
    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private TeachPlaceService teachPlaceService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    /**
     * 
     * @author qiuguojie
     * @Title: list
     * @Description: 课程列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("course/course-table-config-list");
        map.put("isDelete", "2");
        PageObject<CourseTableConfig> list = service.pageList(map);
        Map<String, Object> param = new HashMap<>();
        List<CourseInfo> courseList = courseInfoService.query(param);
        List<TeachPlace> placeList = teachPlaceService.query(param);
        List<TeacherInfo> teacherList = teacherInfoService.query(param);
        andView.addObject("list", list);
        andView.addObject("courseList", courseList);
        andView.addObject("placeList", placeList);
        andView.addObject("teacherList", teacherList);
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
        ModelAndView andView = new ModelAndView("course/course-table-config-add");
        Map<String, Object> param = new HashMap<>();
        List<CourseInfo> courseList = courseInfoService.query(param);
        List<TeachPlace> placeList = teachPlaceService.query(param);
        List<TeacherInfo> teacherList = teacherInfoService.query(param);
        andView.addObject("courseList", courseList);
        andView.addObject("placeList", placeList);
        andView.addObject("teacherList", teacherList);
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
        	
        	Map<String,Object> param = new HashMap<>();
        	param.put("teachId", map.get("teachId"));
        	param.put("teachDate", map.get("teachDate"));
        	List<CourseTableConfig> list = service.query(param);
        	if(list!=null&&list.size()>0){
        		resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"所选教师于"+map.get("teachDate")+"已有课程,请重新安排！");
        		return resultMap;
        	}
        	param.remove("teachId");
        	param.put("placeId", map.get("placeId"));
        	param.put("teachDate", map.get("teachDate"));
        	list = service.query(param);
        	if(list!=null&&list.size()>0){
        		resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"所选站点于"+map.get("teachDate")+"已有课程,请重新安排！");
        		return resultMap;
        	}
        	
        	map.put("createTime", new Date());
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
        ModelAndView andView = new ModelAndView("course/course-table-config-edit");
        CourseTableConfig obj = service.detail(map);
        Map<String, Object> param = new HashMap<>();
        List<CourseInfo> courseList = courseInfoService.query(param);
        List<TeachPlace> placeList = teachPlaceService.query(param);
        List<TeacherInfo> teacherList = teacherInfoService.query(param);
        andView.addObject("courseList", courseList);
        andView.addObject("placeList", placeList);
        andView.addObject("teacherList", teacherList);
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
        	Map<String,Object> param = new HashMap<>();
        	param.put("id", map.get("id"));
        	CourseTableConfig obj = service.detail(param);
        	
        	param.remove("id");
        	param.put("teachId", map.get("teachId"));
        	param.put("teachDate", map.get("teachDate"));
        	List<CourseTableConfig> list = service.query(param);
        	if(list!=null&&list.size()>0){
        		for (int i = list.size()-1; i >=0; i--) {
        			CourseTableConfig courseTableConfig = list.get(i);
					if(courseTableConfig.getId()==obj.getId()){
						list.remove(i);
					}
				}
        		if(list!=null&&list.size()>0){
	        		resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"所选教师于"+map.get("teachDate")+"已有课程,请重新安排！");
	        		return resultMap;
        		}
        	}
        	param.remove("teachId");
        	param.put("placeId", map.get("placeId"));
        	param.put("teachDate", map.get("teachDate"));
        	list = service.query(param);
        	if(list!=null&&list.size()>0){
        		for (int i = list.size()-1; i >=0; i--) {
        			CourseTableConfig courseTableConfig = list.get(i);
					if(courseTableConfig.getId()==obj.getId()){
						list.remove(i);
					}
				}
        		if(list!=null&&list.size()>0){
	        		resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"所选站点于"+map.get("teachDate")+"已有课程,请重新安排！");
	        		return resultMap;
        		}
        	}
        	
        	service.update(map);
        	resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
        return resultMap;
    }
    
    /**
     * 批量修改
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("batchUpdate")
    public Map<String, Object> batchUpdate(@RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	String status = map.get("status").toString();
        	if("3".equals(status)){
        		map.remove("status");
        		map.put("isDelete", 1);
        	}
        	service.batchUpdate(map);
        	resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
        return resultMap;
    }
}
