package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zznode.dhmp.jdbc.datasource.DynamicDataSourceProvider;
import com.zznode.dhmp.jdbc.datasource.config.DynamicDataSourceConfigurer;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * 描述
 *
 * @author 王俊
 */
@Configuration(proxyBeanMethods = false)
public class DataSourceConfiguration implements DynamicDataSourceConfigurer {


    @Override
    public void configureDataSourceProvider(DynamicDataSourceProvider dynamicDataSourceProvider) {
        dynamicDataSourceProvider.addDataSource("configDataSource", configDataSource());
    }


    public HikariDataSource configDataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create(getClass().getClassLoader())
                .type(HikariDataSource.class)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true")
                .username("dhmp")
                .password("dhmp")
                .build();
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }
}
