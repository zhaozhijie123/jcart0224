package com.zhaozhijie.jcartstoreback.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.zhaozhijie.jcartstoreback.dto.in.ProductSearchInDTO;
import com.zhaozhijie.jcartstoreback.dto.out.PageOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductListOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductSearchOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ProductShowOutDTO;
import com.zhaozhijie.jcartstoreback.es.doc.ProductDoc;
import com.zhaozhijie.jcartstoreback.es.repository.ProductRepository;
import com.zhaozhijie.jcartstoreback.mq.HotProductDTO;
import com.zhaozhijie.jcartstoreback.po.ProductOperation;
import com.zhaozhijie.jcartstoreback.service.ProductOperationService;
import com.zhaozhijie.jcartstoreback.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOperationService productOperationService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/search")
    public PageOutDTO<ProductListOutDTO> search(ProductSearchInDTO productSearchInDTO,
                                                  @RequestParam(required = false,defaultValue = "1") Integer pageNum){

        String keyword = productSearchInDTO.getKeyword();
        List<ProductDoc> productDocs = productRepository.findByProductNameLikeOrProductAbstractLike(keyword, keyword);

        System.out.println(productDocs);

        Page<ProductListOutDTO> page = productService.search(productSearchInDTO,pageNum);
        PageOutDTO<ProductListOutDTO> pageOutDTO = new PageOutDTO<>();
        pageOutDTO.setTotal(page.getTotal());
        pageOutDTO.setPageSize(page.getPageSize());
        pageOutDTO.setPageNum(page.getPageNum());
        pageOutDTO.setList(page);
        return pageOutDTO;
    }
    @GetMapping("/getById")
    public ProductShowOutDTO getById(@RequestParam Integer productId){
        ProductShowOutDTO productShowOutDTO = productService.getShowById(productId);
        //todo send msg to kafka
        HotProductDTO hotProductDTO = new HotProductDTO();
        hotProductDTO.setProductId(productId);
        hotProductDTO.setProductCode(productShowOutDTO.getProductCode());

        kafkaTemplate.send("test", JSON.toJSONString(hotProductDTO));
        //productOperationService.count(productId);
        return productShowOutDTO;
    }

    @GetMapping("/hot")
    public List<ProductOperation> hot(){
        String hotProductsJson = redisTemplate.opsForValue().get("HotProducts");
        if (hotProductsJson != null){
            List<ProductOperation> productOperations = JSON.parseArray(hotProductsJson, ProductOperation.class);
            return productOperations;
        }else {
            List<ProductOperation> hotProducts = productOperationService.selectHotProduct();
            redisTemplate.opsForValue().set("HotProducts", JSON.toJSONString(hotProducts),1L, TimeUnit.DAYS);
            return hotProducts;
        }
    }
}
