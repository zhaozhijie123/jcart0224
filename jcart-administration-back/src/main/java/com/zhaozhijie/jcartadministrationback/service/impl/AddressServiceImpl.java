package com.zhaozhijie.jcartadministrationback.service.impl;

import com.zhaozhijie.jcartadministrationback.dao.AddressMapper;
import com.zhaozhijie.jcartadministrationback.po.Address;
import com.zhaozhijie.jcartadministrationback.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Address getById(Integer addressId) {
        Address address = addressMapper.selectByPrimaryKey(addressId);
        return address;
    }
}
