package com.zhaozhijie.jcartstoreback.service;

import com.zhaozhijie.jcartstoreback.dto.in.CustomerRegisterInDTO;
import com.zhaozhijie.jcartstoreback.po.Customer;

public interface CustomerService {

    Integer register(CustomerRegisterInDTO customerRegisterInDTO);

    Customer getByUsername(String username);

    Customer getById(Integer customerId);

    void update(Customer customer);

}
