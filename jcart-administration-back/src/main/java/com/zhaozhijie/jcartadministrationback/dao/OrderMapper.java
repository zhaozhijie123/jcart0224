package com.zhaozhijie.jcartadministrationback.dao;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.dto.out.OrderListOutDTO;
import com.zhaozhijie.jcartadministrationback.po.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Page<OrderListOutDTO> search();
}