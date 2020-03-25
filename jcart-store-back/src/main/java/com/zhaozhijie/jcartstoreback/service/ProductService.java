package com.zhaozhijie.jcartstoreback.service;


import com.github.pagehelper.Page;
import com.zhaozhijie.jcartstoreback.dto.in.ProductSearchInDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductListOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductShowOutDTO;
import com.zhaozhijie.jcartstoreback.po.Product;

public interface ProductService {

    Product getById(Integer productId);

    ProductShowOutDTO getShowById(Integer productId);

    Page<ProductListOutDTO> search(ProductSearchInDTO productSearchInDTO, Integer pageNum);

}
