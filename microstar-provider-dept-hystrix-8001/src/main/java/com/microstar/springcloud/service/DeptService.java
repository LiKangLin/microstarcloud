
package com.microstar.springcloud.service;

import com.microstar.springcloud.entities.Dept;

import java.util.List;

/**
 * Created by PVer on 2020/2/6.
 */
public interface DeptService
{
    public boolean add(Dept dept);

    public Dept get(Long id);

    public List<Dept> list();
}