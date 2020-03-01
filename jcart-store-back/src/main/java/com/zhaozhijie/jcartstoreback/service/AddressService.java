package com.zhaozhijie.jcartstoreback.service;

import com.zhaozhijie.jcartstoreback.po.Address;

import java.util.List;

public interface AddressService {

    List<Address> getByCustomerId(Integer customerId);

    Integer create(Address address);

    void update(Address address);

    void delete(Integer addressId);

}
