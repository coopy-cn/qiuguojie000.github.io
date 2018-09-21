package com.orye.manger.controller.user;

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
import com.orye.business.course.entity.CourseUser;
import com.orye.business.course.service.CourseUserService;
import com.orye.business.teach.entity.HomePerson;
import com.orye.business.teach.service.HomePersonService;
import com.orye.manger.controller.course.CourseInfoController;

/**
 * @ClassName: CourseInfoController
 * @Description: 用户管理
 * @author qiuguojie
 * @date 2018年08月21日 上午10:46:10
 * 
 */
@RequestMapping("/courseUser/*")
@Controller
public class CourseUserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseInfoController.class);
	
	@Autowired
	private CourseUserService service;
	@Autowired
	private HomePersonService homePersonService;
	/**
     * 
     * @author qiuguojie
     * @Title: list
     * @Description: 用户列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("user/course-user-list");
        PageObject<CourseUser> list = service.Pagequery(map);
        andView.addObject("list", list);
        andView.addObject("map", map);
        return andView;
    }
    
    /**
     * 新增
     * @param map
     * @return
     */
    @RequestMapping("homeList")
    public ModelAndView homeList(@RequestParam Map<String, Object> map) {
    	ModelAndView andView = new ModelAndView("user/home-person-list");
    	CourseUser user = service.detail(map);
    	Map<String, Object> param = new HashMap<>();
    	param.put("openId", user.getOpenId());
    	List<HomePerson> list = homePersonService.query(param);
        andView.addObject("list", list);
        andView.addObject("map", map);
        return andView;
    	
    }
}
