package com.zhaozhijie.jcartadministrationback.controller;

import com.zhaozhijie.jcartadministrationback.dto.in.AdministratorResetPwdInDTO;
import com.zhaozhijie.jcartadministrationback.dto.in.AdministratorUpdateProfileInDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.AdministratorGetProfileOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.AdministratorListOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.PageOutDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    @GetMapping("/login")
    public String login(){
        return null;
    }

    @GetMapping("/getProfile")
    public AdministratorGetProfileOutDTO getProfile(@RequestParam(required = false) Integer administratorId){
        return null;
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody AdministratorUpdateProfileInDTO administratorUpdateProfileInDTO){

    }

    @GetMapping("/getPwdResetCode")
    public String getPwdResetCode(@RequestParam String email){
        return null;
    }

    @PostMapping("/resetPwd")
    public void resetPwd(@RequestBody AdministratorResetPwdInDTO administratorResetPwdInDTO){

    }

    @GetMapping("/getList")
    public PageOutDTO<AdministratorListOutDTO> getList(@RequestParam Integer pageNum){
        return null;
    }

}
