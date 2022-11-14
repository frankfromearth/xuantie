package com.xuantie.qidaren;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.xuantie.qidaren.web.RequestCacheFilter;

/**
 * spring boot 启动类.
 * Created by xuhh on 2018/4/26
 */
@SpringBootApplication  
@EnableCaching
@MapperScan("com.xuantie.qidaren.dao")
public class Application  {  
    public static void main(String[] args )  
    {  
        SpringApplication.run(Application.class, args);  
    }  
    
    /** 
     * 注册过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistration(){
    	FilterRegistrationBean registration = new FilterRegistrationBean(new RequestCacheFilter());
    	registration.addUrlPatterns("/*");
    	return registration;
    }
    
  

} 