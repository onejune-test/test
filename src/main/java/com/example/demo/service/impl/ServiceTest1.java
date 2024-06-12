package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author 王俊
 */
@Service
public class ServiceTest1 {
    public ServiceTest1() {
        System.out.println("ServiceTest1");
    }

    @Autowired
    private ServiceTest2 serviceTest2;

    @Async
    public void test1() {

    }
}
