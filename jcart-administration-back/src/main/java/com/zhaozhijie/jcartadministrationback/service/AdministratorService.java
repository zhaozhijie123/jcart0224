package com.zhaozhijie.jcartadministrationback.service;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.dto.in.AdministratorResetPwdInDTO;
import com.zhaozhijie.jcartadministrationback.exception.ClientException;
import com.zhaozhijie.jcartadministrationback.po.Administrator;

import java.util.List;
import java.util.Map;

public interface AdministratorService {

    Administrator getById(Integer administratorId);

    Administrator getByUsername(String username);

    Integer create(Administrator administrator);

    void update(Administrator administrator);

    void delete(Integer administratorId);

    void batchDelete(List<Integer> administratorIds);

    Page<Administrator> getList(Integer pageNum);

    String getByEmail(String email) throws ClientException;

    void restPwd(AdministratorResetPwdInDTO administratorResetPwdInDTO) throws ClientException;
}
