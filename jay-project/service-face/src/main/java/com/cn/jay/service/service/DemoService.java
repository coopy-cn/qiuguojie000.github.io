package com.cn.jay.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.jay.business.data1.entity.Gaccount;
import com.cn.jay.business.data1.mapper.GaccountMapper;
import com.cn.jay.business.data2.entity.Guser;
import com.cn.jay.business.data2.mapper.GuserMapper;

@Component
public class DemoService {
	@Autowired
    private GaccountMapper gaccountMapper;
	
	@Autowired
    private GuserMapper guserMapper;
	
	public List<Gaccount> list(){
		Guser tt = new Guser();
		List<Guser> list = guserMapper.selectByObject(tt);
		System.out.println(list.size());
		
		Gaccount t = new Gaccount();
		return gaccountMapper.selectByObject(t);
	}
}
