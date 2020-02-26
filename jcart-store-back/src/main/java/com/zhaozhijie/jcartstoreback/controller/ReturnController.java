package com.zhaozhijie.jcartstoreback.controller;

import com.zhaozhijie.jcartstoreback.dto.in.ReturnApplyInDTO;
import com.zhaozhijie.jcartstoreback.dto.out.PageOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ReturnListOutDTO;
import com.zhaozhijie.jcartstoreback.dto.out.ReturnShowOutDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/return")
public class ReturnController {
    @PostMapping("/apply")
    public Integer apply(@RequestBody ReturnApplyInDTO returnApplyInDTO, @RequestAttribute Integer customerId){
        return null;
    }

    @GetMapping("/getList")
    public PageOutDTO<ReturnListOutDTO> getList(@RequestAttribute Integer customerId, @RequestParam Integer pageNum){
        return null;
    }

    @GetMapping("/getById")
    public ReturnShowOutDTO getById(@RequestParam Integer returnId){
        return null;
    }
}
