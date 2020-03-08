package com.zhaozhijie.jcartadministrationback.service;

import com.zhaozhijie.jcartadministrationback.po.OrderHistory;

import java.util.List;

public interface OrderHistoryService {
    List<OrderHistory> getByOrderId(Long orderId);

    Long create(OrderHistory orderHistory);
}
