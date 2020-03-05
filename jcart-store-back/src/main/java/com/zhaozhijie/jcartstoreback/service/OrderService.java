package com.zhaozhijie.jcartstoreback.service;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartstoreback.dto.in.OrderCheckoutInDTO;
import com.zhaozhijie.jcartstoreback.dto.out.OrderShowOutDTO;
import com.zhaozhijie.jcartstoreback.po.Order;

public interface OrderService {

    Long checkout(OrderCheckoutInDTO orderCheckoutInDTO,
                  Integer customerId);

    Page<Order> getByCustomerId(Integer pageNum, Integer customerId);

    OrderShowOutDTO getById(Long orderId);

}
