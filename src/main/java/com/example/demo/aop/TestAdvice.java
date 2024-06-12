package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.Ordered;

/**
 * 描述
 *
 * @author 王俊
 */
@Slf4j
public class TestAdvice implements MethodInterceptor, Ordered {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        log.info("before: {}", invocation.getMethod());
        Object proceed = invocation.proceed();
        log.info("after");

        return proceed;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE + 3;
    }
}
