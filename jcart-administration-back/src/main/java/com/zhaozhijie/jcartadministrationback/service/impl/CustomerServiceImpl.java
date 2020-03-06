package com.zhaozhijie.jcartadministrationback.service.impl;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.dao.CustomerMapper;
import com.zhaozhijie.jcartadministrationback.po.Customer;
import com.zhaozhijie.jcartadministrationback.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Page<Customer> search(Integer pageNum) {
        return customerMapper.search();
    }

    @Override
    public Customer getById(Integer customerId) {
        Customer customer = customerMapper.selectByPrimaryKey(customerId);
        return customer;
    }
}
