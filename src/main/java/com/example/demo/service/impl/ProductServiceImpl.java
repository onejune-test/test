package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductExport;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.ProductService;
import com.mybatisflex.core.query.QueryWrapper;
import com.zznode.dhmp.data.process.sensitive.annotation.Sensitive;
import com.zznode.dhmp.jdbc.datasource.annotation.UseDynamicDataSource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述
 *
 * @author 王俊
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
//    @Export(ProductExport.class)
    @UseDynamicDataSource("test")
    public List<ProductExport> list() {

        return productMapper.selectListByQueryAs(QueryWrapper.create(), ProductExport.class);
    }

    @Override
    @Sensitive
    public List<Product> productList() {
        return productMapper.paginate(1, 20, QueryWrapper.create()).getRecords();
    }
}
