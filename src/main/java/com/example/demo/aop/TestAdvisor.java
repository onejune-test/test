package com.example.demo.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author 王俊
 */
public class TestAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new AnnotationMatchingPointcut(Service.class);
    }
}
