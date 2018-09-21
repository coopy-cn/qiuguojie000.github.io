package com.orye.manger.controller.teach;

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
import com.orye.business.teach.entity.CityRegion;
import com.orye.business.teach.entity.TeachPlace;
import com.orye.business.teach.service.CityRegionService;
import com.orye.business.teach.service.TeachPlaceService;
import com.orye.business.util.ParameterEunm;

/**
 * @ClassName: TeacherInfoController
 * @Description: 课程信息相关
 * @author qiuguojie
 * @date 2018年08月21日 上午10:46:10
 * 
 */
@RequestMapping("/teachPlace/*")
@Controller
public class TeachPlaceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TeachPlaceController.class);
    @Autowired
    private TeachPlaceService service;
    @Autowired
    private CityRegionService cityRegionService;
    
    /**
     * 
     * @author qiuguojie
     * @Title: list
     * @Description: 课程列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("teach/teach-place-list");
        PageObject<TeachPlace> list = service.getPageList(map);
        Map<String, Object> param = new HashMap<>();
        //所有区
        List<CityRegion> regList= cityRegionService.query(param);
        param.put("parentId", 0);
        //所有市
        List<CityRegion> cityList= cityRegionService.query(param);
        andView.addObject("map", map);
        andView.addObject("list", list);
        andView.addObject("regList", regList);
        andView.addObject("cityList", cityList);
        return andView;
    }
    
    /**
     * 去新增页面
     * @param map
     * @return
     */
    @RequestMapping("/addToPage")
    public ModelAndView addToPage(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("teach/teach-place-add");
        Map<String, Object> param = new HashMap<>();
        //所有区
        List<CityRegion> regList= cityRegionService.query(param);
        param.put("parentId", 0);
        //所有市
        List<CityRegion> cityList= cityRegionService.query(param);
        andView.addObject("regList", regList);
        andView.addObject("cityList", cityList);
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
        	String mapPosition = map.get("mapPosition").toString();
        	if(mapPosition.indexOf(",")!=-1){
	        	String position[] = mapPosition.split(",");
	        	map.put("longitude", position[0]);
	        	map.put("latitude", position[1]);
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
        ModelAndView andView = new ModelAndView("teach/teach-place-edit");
        TeachPlace obj = service.detail(map);
        Map<String, Object> param = new HashMap<>();
        param.put("id", obj.getRegId());
        CityRegion cityRegion = cityRegionService.detail(param);
        param.clear();
        
        List<CityRegion> allList= cityRegionService.query(param);
        
        param.put("parentId", cityRegion.getParentId());
        List<CityRegion> regList= cityRegionService.query(param);
        param.put("parentId", 0);
        //所有市
        List<CityRegion> cityList= cityRegionService.query(param);
        andView.addObject("regList", regList);
        andView.addObject("cityList", cityList);
        andView.addObject("cityRegion", cityRegion);
        andView.addObject("allList", allList);
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
        	String mapPosition = map.get("mapPosition").toString();
        	if(mapPosition.indexOf(",")!=-1){
	        	String position[] = mapPosition.split(",");
	        	map.put("longitude", position[0]);
	        	map.put("latitude", position[1]);
	        	service.update(map);
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
     * 去新增页面
     * @param map
     * @return
     */
    @RequestMapping("/toMap")
    public ModelAndView toMap(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("teach/map");
        return andView;
    }
}
