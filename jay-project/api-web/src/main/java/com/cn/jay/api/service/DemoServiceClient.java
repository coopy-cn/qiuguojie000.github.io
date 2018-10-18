package com.cn.jay.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cn.jay.business.util.MyBase64;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class DemoServiceClient {
	@Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "selectMchInfoFallback")
    public String selectMchInfo(String jsonParam) {
        return restTemplate.getForEntity("http://SERVICE-FACE/demo?jsonParam=" + MyBase64.encode(jsonParam.getBytes()), String.class).getBody();
    }

    public String selectMchInfoFallback(String jsonParam) {
        return "error";
    }
}
