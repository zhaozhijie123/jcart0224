package com.zhaozhijie.jcartadministrationback.service;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.dto.in.ProductCreateInDTO;
import com.zhaozhijie.jcartadministrationback.dto.in.ProductUpdateInDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.ProductListOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.ProductShowOutDTO;

import java.util.List;

public interface ProductService {

    Integer create(ProductCreateInDTO productCreateInDTO);

    void update(ProductUpdateInDTO productUpdateInDTO);

    void delete(Integer productId);

    void batchDelete(List<Integer> productIds);

    Page<ProductListOutDTO> search(Integer pageNum);

    ProductShowOutDTO getById(Integer productId);
}
