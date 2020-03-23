package com.zhaozhijie.jcartstoreback.controller;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartstoreback.dto.in.ProductSearchInDTO;
import com.zhaozhijie.jcartstoreback.dto.out.PageOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductListOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductSearchOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductShowOutDTO;
import com.zhaozhijie.jcartstoreback.service.ProductOperationService;
import com.zhaozhijie.jcartstoreback.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOperationService productOperationService;

    @GetMapping("/search")
    public PageOutDTO<ProductListOutDTO> search(ProductSearchInDTO productSearchInDTO,
                                                  @RequestParam(required = false,defaultValue = "1") Integer pageNum){
        Page<ProductListOutDTO> page = productService.search(pageNum);
        PageOutDTO<ProductListOutDTO> pageOutDTO = new PageOutDTO<>();
        pageOutDTO.setTotal(page.getTotal());
        pageOutDTO.setPageSize(page.getPageSize());
        pageOutDTO.setPageNum(page.getPageNum());
        pageOutDTO.setList(page);
        return pageOutDTO;
    }
    @GetMapping("/getById")
    public ProductShowOutDTO getById(@RequestParam Integer productId){
        System.out.println(productId);
        ProductShowOutDTO productShowOutDTO = productService.getShowById(productId);
        //todo send msg to kafka
        productOperationService.count(productId);
        return productShowOutDTO;
    }

    @GetMapping("/hot")
    public List<ProductListOutDTO> hot(){
        return null;
    }
}
