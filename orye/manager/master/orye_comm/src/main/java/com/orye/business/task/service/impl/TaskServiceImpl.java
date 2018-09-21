package com.orye.business.task.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.teamwork.base.util.date.DateUtil;
import com.orye.business.task.service.TaskService;
import com.orye.business.teach.entity.HomePerson;
import com.orye.business.teach.service.HomePersonService;

@Service("taskService")
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private HomePersonService homePersonService;
		
	@Override
	public void homePersonSave(Map<String,Object> map) throws Exception {
		Map<String,Object> param = new HashMap<>();
		//判断爸爸是否保存
		param.put("openId", map.get("sk"));
		param.put("type", 1);
		Date date = new Date();
		HomePerson homePerson = homePersonService.detail(param);
		if(homePerson==null){
			homePerson = new HomePerson();
			homePerson.setAddress(map.get("address").toString());
			homePerson.setEmail(map.get("email").toString());
			homePerson.setName(map.get("fatName").toString());
			homePerson.setPhone(map.get("fatPhone").toString());
			homePerson.setSex(1);
			homePerson.setType(1);
			homePerson.setOpenId(map.get("sk").toString());
			homePerson.setCreateTime(date);
			homePersonService.add(homePerson);
		}else{
			homePerson.setAddress(map.get("address").toString());
			homePerson.setEmail(map.get("email").toString());
			homePerson.setName(map.get("fatName").toString());
			homePerson.setPhone(map.get("fatPhone").toString());
			homePersonService.update(homePerson);
		}
		//判断妈妈是否保存
		param.put("type", 2);
		homePerson = homePersonService.detail(param);
		if(homePerson==null){
			homePerson = new HomePerson();
			homePerson.setName(map.get("motName").toString());
			homePerson.setPhone(map.get("motPhone").toString());
			homePerson.setSex(2);
			homePerson.setType(2);
			homePerson.setOpenId(map.get("sk").toString());
			homePerson.setCreateTime(date);
			homePersonService.add(homePerson);
		}else{
			homePerson.setName(map.get("motName").toString());
			homePerson.setPhone(map.get("motPhone").toString());
			homePersonService.update(homePerson);
		}
		//判断小孩是否保存
		param.put("type", 3);
		param.put("name", map.get("chirldName"));
		homePerson = homePersonService.detail(param);
		if(homePerson==null){
			homePerson = new HomePerson();
			homePerson.setName(map.get("chirldName").toString());
			homePerson.setSex(Integer.valueOf(map.get("sex").toString()));
			homePerson.setOpenId(map.get("sk").toString());
			homePerson.setType(3);
			homePerson.setBirthDate(DateUtil.StringToDate(map.get("birthDate").toString()));
			homePerson.setCreateTime(date);
			homePersonService.add(homePerson);
		}else{
			homePerson.setSex(Integer.valueOf(map.get("sex").toString()));
			homePerson.setBirthDate(DateUtil.StringToDate(map.get("birthDate").toString()));
			homePersonService.update(homePerson);
		}
	}
	
}
