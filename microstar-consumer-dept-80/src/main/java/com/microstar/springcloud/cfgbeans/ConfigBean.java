package com.microstar.springcloud.cfgbeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by PVer on 2020/2/7.
 */
@Configuration
public class ConfigBean
{
    @Bean
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
}


/*@Configuration 和@Bean
* 相等于spring中的
* applicationContext.xml文件
*
*
* */
