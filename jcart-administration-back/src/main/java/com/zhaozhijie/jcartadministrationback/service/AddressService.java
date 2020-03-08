package com.zhaozhijie.jcartadministrationback.service;

import com.zhaozhijie.jcartadministrationback.po.Address;

import java.util.List;

public interface AddressService {
    Address getById(Integer addressId);

    List<Address> getByCustomerId(Integer customerId);
}
