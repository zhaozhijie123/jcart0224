package com.zhaozhijie.jcartadministrationback.dao;

import com.zhaozhijie.jcartadministrationback.po.ProductOperation;

public interface ProductOperationMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(ProductOperation record);

    int insertSelective(ProductOperation record);

    ProductOperation selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(ProductOperation record);

    int updateByPrimaryKey(ProductOperation record);
}