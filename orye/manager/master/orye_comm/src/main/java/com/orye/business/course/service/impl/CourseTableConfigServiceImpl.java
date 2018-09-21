package com.orye.business.course.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.teamwork.base.service.impl.BaseServiceImpl;
import com.core.teamwork.base.util.ReadProChange;
import com.core.teamwork.base.util.date.DateStyle;
import com.core.teamwork.base.util.date.DateUtil;
import com.core.teamwork.base.util.page.PageHelper;
import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.course.entity.CourseOrder;
import com.orye.business.course.entity.CourseTableConfig;
import com.orye.business.course.mapper.CourseOrderMapper;
import com.orye.business.course.mapper.CourseTableConfigMapper;
import com.orye.business.course.service.CourseTableConfigService;

/**
 * @author cyl
 * @version 
 */
@Service("courseTableConfigService")
public class CourseTableConfigServiceImpl extends BaseServiceImpl<CourseTableConfig, CourseTableConfigMapper> implements CourseTableConfigService {
	// 注入当前dao对象
    @Autowired
    private CourseTableConfigMapper courseTableConfigMapper;
    @Autowired
    private CourseOrderMapper courseOrderMapper;

    public CourseTableConfigServiceImpl() {
        setMapperClass(CourseTableConfigMapper.class, CourseTableConfig.class);
    }
    
    /**
     * （课程预约）-课程列表（小程序接口）
     */
    public PageObject<CourseTableConfig> pageList(Map<String,Object> map){
    	
    	if(map.containsKey("time")){
    		if("1".equals(map.get("time").toString())){
    			map.put("time", "09:30");
    		}else if("2".equals(map.get("time").toString())){
    			map.put("time", "16:00");
    		}else{
    			map.remove("time");
    		}
    	}
    	if(map.containsKey("date")){
    		JSONArray date = JSONArray.parseArray(map.get("date").toString());
    		String str = "";
    		for (Object object : date) {
				JSONObject obj = (JSONObject)object;
				if("true".equals(obj.getString("checked"))){
					if("0".equals(obj.getString("value"))){
						str=str+obj.getString("year")+"-"+DateUtil.DateToString(new Date(), DateStyle.MM_DD)+",";
					}else{
						str=str+obj.getString("year")+"-"+obj.getString("name")+",";
					}
				}
			}
    		if(!"".equals(str)){
    			map.put("date", str.substring(0, str.length()-1).split(","));
    		}else{
    			map.remove("date");
    		}
    	}
    	
    	map.put("amountType", 1);
    	if(map.containsKey("sk")){
			Map<String,Object> param = new HashMap<>();
			param.put("openId", map.get("sk"));
			param.put("status", 2);
			CourseOrder obj = courseOrderMapper.queryLast(param);
			if(obj!=null){
				map.put("amountType", 2);
			}
		}
    	
    	int totalData = courseTableConfigMapper.pageListCount(map);
        PageHelper pageHelper = new PageHelper(totalData, map);
        List<CourseTableConfig> list = courseTableConfigMapper.pageList(pageHelper.getMap());
        PageObject<CourseTableConfig> pageObject = pageHelper.getPageObject();
        pageObject.setDataList(list);
        return pageObject;
    }
    
    public void batchUpdate(Map<String,Object> map){
    	courseTableConfigMapper.batchUpdate(map);
    }
}
