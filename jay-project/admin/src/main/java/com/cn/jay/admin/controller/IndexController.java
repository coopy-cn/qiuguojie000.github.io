package com.cn.jay.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.jay.admin.service.DemoServcie;
import com.cn.jay.business.data1.entity.Gaccount;

@Api(value="测试",tags={"测试类"})
@RestController
public class IndexController {
	
	@Autowired
	private DemoServcie demo;
	
	@ApiOperation(value="哈喽")
	@RequestMapping(value="/hello",method=RequestMethod.POST)
	public Map<String,Object> hello(@ApiParam(value="用户年龄",required=true) @RequestParam(value="ages")Integer age) {
		String getConf="hello, sping Cloud!";
		List<Gaccount> list= demo.list();
		System.out.println(list.size());
		Map<String,Object> map = new HashMap<>();
		map.put("sss", getConf);
		map.put("list", list);
		return map;
	}
}
