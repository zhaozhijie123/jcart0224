package com.zhaozhijie.jcartadministrationback.service;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.po.Customer;

public interface CustomerService {

    Page<Customer> search(Integer pageNum);

    Customer getById(Integer customerId);
}
