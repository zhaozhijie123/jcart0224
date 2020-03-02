package com.zhaozhijie.jcartadministrationback.service.impl;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.dao.AdministratorMapper;
import com.zhaozhijie.jcartadministrationback.po.Administrator;
import com.zhaozhijie.jcartadministrationback.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorMapper administratorMapper;

    @Override
    public Administrator getById(Integer administratorId) {
        return null;
    }

    @Override
    public Administrator getByUsername(String username) {
        Administrator administrator = administratorMapper.selectByUsername(username);
        return administrator;
    }

    @Override
    public Integer create(Administrator administrator) {
        return null;
    }

    @Override
    public void update(Administrator administrator) {

    }

    @Override
    public void delete(Integer administratorId) {

    }

    @Override
    public void batchDelete(List<Integer> administratorIds) {

    }

    @Override
    public Page<Administrator> getList(Integer pageNum) {
        return null;
    }
}
