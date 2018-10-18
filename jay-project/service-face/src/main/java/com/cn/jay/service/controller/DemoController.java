package com.cn.jay.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.jay.business.data1.entity.Gaccount;
import com.cn.jay.service.service.DemoService;

@RestController
public class DemoController {
	
	@Autowired
	private DemoService demo;
	
    @RequestMapping(value = "/demo")
    public String demo(@RequestParam String jsonParam) {
        
    	System.out.println("**************************");
    	System.out.println("66666");
    	
    	List<Gaccount> list= demo.list();
		System.out.println(list.size());
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
    	
        return "ss";
    }
}
