package com.example.demo.config;

import com.example.demo.entity.ProductExport;
import com.example.demo.mapper.ProductMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.zznode.dhmp.export.support.exporter.ExporterFactory;
import com.zznode.dhmp.export.web.mvc.function.ExportHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * 描述
 *
 * @author 王俊
 */
@Configuration
public class HandlerFunctionsConfiguration {


    @Bean
    public RouterFunction<ServerResponse> routerFunction(ExporterFactory exporterFactory, ProductMapper productMapper) {
        return RouterFunctions.route()
                .GET("/test/export", ExportHandlerFunction
                        .handle(r -> productMapper.selectListByQueryAs(QueryWrapper.create(), ProductExport.class))
                        .exportFactory(exporterFactory)
                        .export(ProductExport.class)
                )
                .build();
    }
}
