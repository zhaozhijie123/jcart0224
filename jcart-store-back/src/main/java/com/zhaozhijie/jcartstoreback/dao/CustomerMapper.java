package com.zhaozhijie.jcartstoreback.dao;

import com.zhaozhijie.jcartstoreback.po.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapper {
    int deleteByPrimaryKey(Integer customerId);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer customerId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    Customer selectByUsername(String username);

    Customer selectByEmail(String email);
}