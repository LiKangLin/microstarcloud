package com.microstar.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by PVer on 2020/2/12.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class DeptZuul9527_App {
    public static void main(String[] args)
    {
        SpringApplication.run(DeptZuul9527_App.class, args);
    }

}
