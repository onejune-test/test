package com.example.demo.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author 王俊
 */
@Service
public class ServiceTest2 {
    private final ServiceTest1 serviceTest1;

    @Lazy
    public ServiceTest2(ServiceTest1 serviceTest1) {
        this.serviceTest1 = serviceTest1;
    }
}
