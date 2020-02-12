package com.microstar.springcloud.controller;

import com.microstar.springcloud.entities.Dept;
import com.microstar.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by PVer on 2020/2/6.
 */
@RestController
public class DeptController {

    @Autowired
    private DeptService service = null;

    @RequestMapping(value="/dept/get/{id}",method= RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "processHystrix_Get")
    /**
     * 一旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的
     * fallbackMethod调用类中的指定方法
     */
    public Dept get(@PathVariable("id") Long id)
    {
        Dept dept =  this.service.get(id);
        if(null == dept)
        {
            throw new RuntimeException("该ID："+id+"没有没有对应的信息");
        }
        return dept;
    }

    public Dept processHystrix_Get(@PathVariable("id") Long id)
    {
        Dept dept=new Dept();
        dept.setDeptno(id);
        dept.setDname("该ID："+id+"没有没有对应的信息,null--@HystrixCommand");
        dept.setDb_source("no this database in MySQL");
        return dept;
        /*return new Dept().setDeptno(id)
                .setDname("该ID："+id+"没有没有对应的信息,null--@HystrixCommand")
                .setDb_source("no this database in MySQL");*/
    }

}
