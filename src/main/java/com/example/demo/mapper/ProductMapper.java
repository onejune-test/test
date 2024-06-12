package com.example.demo.mapper;

import com.example.demo.entity.Product;
import com.mybatisflex.core.BaseMapper;
import com.zznode.dhmp.jdbc.datasource.annotation.UseDynamicDataSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author 王俊
 * @date create in 2023/8/29
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    int batchInsert(List<Product> productList);

    @UseDynamicDataSource()
    Product selectOne();
}
