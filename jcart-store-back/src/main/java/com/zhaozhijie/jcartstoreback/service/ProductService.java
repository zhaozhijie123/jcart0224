package com.zhaozhijie.jcartstoreback.service;


import com.github.pagehelper.Page;
import com.zhaozhijie.jcartstoreback.dto.out.ProductListOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductShowOutDTO;

public interface ProductService {

    ProductShowOutDTO getById(Integer productId);

    Page<ProductListOutDTO> search(Integer pageNum);

}
