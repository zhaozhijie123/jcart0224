package com.zhaozhijie.jcartstoreback.service;

import com.zhaozhijie.jcartstoreback.po.ProductOperation;

import java.util.List;

public interface ProductOperationService {

    void count(Integer productId);

    List<ProductOperation> selectHotProduct();
}
