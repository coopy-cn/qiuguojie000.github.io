package com.cn.jay.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.jay.api.service.DemoServiceClient;

@RestController
public class DemoController {
	
	@Autowired
    private DiscoveryClient client;	
	@Autowired
    private DemoServiceClient demoServiceClient;
	
    @RequestMapping(value = "/demoTest")
    public String payOrder(@RequestParam String params) {
        ServiceInstance instance = client.getLocalServiceInstance();
        
        System.out.println("#################"+params+instance.getHost()+ instance.getServiceId());
        
        return demoServiceClient.selectMchInfo(params);
        		
    }
}
