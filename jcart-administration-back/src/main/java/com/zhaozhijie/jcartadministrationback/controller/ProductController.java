package com.zhaozhijie.jcartadministrationback.controller;

import com.zhaozhijie.jcartadministrationback.dto.in.ProductSearchInDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.PageOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.ProductListOutDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/search")
    public PageOutDTO<ProductListOutDTO> search(ProductSearchInDTO productSearchInDTO,
                                                @RequestParam Integer pageNum){
        return null;
    }

}
