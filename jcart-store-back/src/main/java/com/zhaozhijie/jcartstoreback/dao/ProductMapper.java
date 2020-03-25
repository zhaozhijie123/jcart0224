package com.zhaozhijie.jcartstoreback.dao;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartstoreback.dto.out.ProductListOutDTO;
import com.zhaozhijie.jcartstoreback.po.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Page<ProductListOutDTO> search(@Param("keyword") String keyword,@Param("status") byte status);
}