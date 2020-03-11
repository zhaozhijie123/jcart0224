package com.zhaozhijie.jcartadministrationback.dao;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.dto.in.ProductSearchInDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.ProductListOutDTO;
import com.zhaozhijie.jcartadministrationback.po.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

//    custom

    int batchDelete(@Param("productIds") List<Integer> productIds);

    Page<ProductListOutDTO> search(@Param("productCode") String productCode,
                                   @Param("status") Byte status,
                                   @Param("stockQuantity") Integer stockQuantity,
                                   @Param("price") Double price,
                                   @Param("productName") String productName);

}