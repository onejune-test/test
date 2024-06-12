package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.TestService;
import com.zznode.dhmp.jdbc.datasource.annotation.UseDynamicDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述
 *
 * @author 王俊
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService, DisposableBean {
    private final ProductMapper productMapper;

    public TestServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @PostConstruct
    public void setUp() {
        log.info("init TestService");
    }

    @PreDestroy
    public void destroy1() {

        log.info("destroy1 TestService");
    }

    public void destroy() {

        log.info("destroy2 TestService");
    }

    @Override
    @Async
    @UseDynamicDataSource("configDataSource")
    public void test() {
        log.info("Test service running,{}, virtual: {}", Thread.currentThread(), Thread.currentThread().isVirtual());

        Product product = productMapper.selectOne();
        List<Product> productList = productMapper.selectAll();
        log.info("all: {}", productList.size());

        log.info("Test service completed");
    }

    @Override
    public void test1(String name) {
        if (name == null) {
            throw new RuntimeException("aaa");
        }
        log.info("param, {}", name);
    }

}
