package com.zhaozhijie.jcartadministrationback.service;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.dto.out.OrderListOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.OrderShowOutDTO;
import com.zhaozhijie.jcartadministrationback.po.Order;

public interface OrderService {
    Page<OrderListOutDTO> search(Integer pageNum);

    OrderShowOutDTO getById(Long orderId);

    void update(Order order);
}
