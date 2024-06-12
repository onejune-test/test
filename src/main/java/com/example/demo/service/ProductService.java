package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductExport;

import java.util.List;

/**
 * 描述
 *
 * @author 王俊
 */
public interface ProductService {

    List<ProductExport> list();

    List<Product> productList();
}
