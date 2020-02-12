package com.microstar.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by PVer on 2020/2/6.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class DeptProvider8001_hystrix_App {
    public static void main(String[] args)
    {
        SpringApplication.run(DeptProvider8001_hystrix_App.class, args);
    }
}
