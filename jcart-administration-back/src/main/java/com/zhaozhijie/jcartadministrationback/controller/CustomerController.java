package com.zhaozhijie.jcartadministrationback.controller;

import com.zhaozhijie.jcartadministrationback.dto.in.CustomerSearchInDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.CustomerListOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.CustomerShowOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.PageOutDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/search")
    public PageOutDTO<CustomerListOutDTO> search(CustomerSearchInDTO customerSearchInDTO,
                                                 @RequestParam Integer pageNum){
        return null;
    }

    @GetMapping("/getById")
    public CustomerShowOutDTO getById(@RequestParam Integer customerId){
        return null;
    }

    @PostMapping("/disable")
    public void disable(@RequestParam Integer customerId){

    }

}
