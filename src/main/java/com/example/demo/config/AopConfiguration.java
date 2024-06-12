package com.example.demo.config;

import com.example.demo.aop.TestAdvice;
import com.example.demo.aop.TestAdvisor;
import com.zznode.dhmp.context.condition.ConditionalOnProvince;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * 描述
 *
 * @author 王俊
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AopConfiguration implements MessageSourceAware {

    @Bean
    @ConditionalOnProvince
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TestAdvice testAdvice(){
        return new TestAdvice();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TestAdvisor testAdvisor(TestAdvice testAdvice) {
        TestAdvisor testAdvisor = new TestAdvisor();
        testAdvisor.setAdvice(testAdvice);
        return testAdvisor;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

    }
}
