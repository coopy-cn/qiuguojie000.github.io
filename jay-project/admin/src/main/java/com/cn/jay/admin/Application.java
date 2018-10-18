package com.cn.jay.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration//配置控制  
//@EnableAutoConfiguration//启用自动配置  
//@EnableDiscoveryClient
//@EnableEurekaClient
//@ComponentScan(value={"com.cn.jay.controller","com.cn.jay.service"})//组件扫描 
//@MapperScan(basePackages = {"com.cn.jay.business.data1.mapper"})
@EnableSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application extends SpringBootServletInitializer{  
    public static void main(String[] args) {     
        SpringApplication.run(Application.class,args);     
    }     
    
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}  
