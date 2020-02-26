package com.zhaozhijie.jcartstoreback.controller;

import com.zhaozhijie.jcartstoreback.dto.in.ProductSearchInDTO;
import com.zhaozhijie.jcartstoreback.dto.out.PageOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductSearchOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductShowOutDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @GetMapping("/search")
    public PageOutDTO<ProductSearchOutDTO> search(@RequestBody ProductSearchInDTO productSearchInDTO,
                                                  @RequestParam Integer pageNum){
        return null;
    }
    @GetMapping("/getById")
    public ProductShowOutDTO getById(@RequestParam Integer productId){
        return null;
    }
}
