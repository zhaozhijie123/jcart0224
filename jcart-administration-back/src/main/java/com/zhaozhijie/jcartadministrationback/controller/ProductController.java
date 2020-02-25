package com.zhaozhijie.jcartadministrationback.controller;

import com.zhaozhijie.jcartadministrationback.dto.in.ProductCreateInDTO;
import com.zhaozhijie.jcartadministrationback.dto.in.ProductSearchInDTO;
import com.zhaozhijie.jcartadministrationback.dto.in.ProductUpdateInDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.PageOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.ProductListOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.ProductShowOutDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/search")
    public PageOutDTO<ProductListOutDTO> search(ProductSearchInDTO productSearchInDTO,
                                                @RequestParam Integer pageNum){
        return null;
    }

    @GetMapping("/getById")
    public ProductShowOutDTO getById(@RequestParam Integer productId){
        return null;
    }

    @PostMapping("/create")
    public Integer create(@RequestBody ProductCreateInDTO productCreateInDTO){
        return null;
    }

    @PostMapping("/update")
    public void update(@RequestBody ProductUpdateInDTO productUpdateInDTO){

    }
}
