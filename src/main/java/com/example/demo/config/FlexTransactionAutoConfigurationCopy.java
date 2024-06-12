package com.example.demo.config;

import com.mybatisflex.spring.FlexTransactionManager;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * 重写{@link com.mybatisflex.spring.boot.FlexTransactionAutoConfiguration}, 添加@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
 *
 * @author 王俊
 */
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class FlexTransactionAutoConfigurationCopy implements TransactionManagementConfigurer {

    private final TransactionManager flexTransactionManager;

    public FlexTransactionAutoConfigurationCopy(TransactionManager transactionManager) {
        this.flexTransactionManager = transactionManager;
    }

    @NonNull
    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return flexTransactionManager;
    }


    @Configuration(proxyBeanMethods = false)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    static class FlexTransactionConfiguration {
        @Bean(name = "transactionManager")
        @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
        public TransactionManager annotationDrivenTransactionManager() {
            return new FlexTransactionManager();
        }
    }
}
