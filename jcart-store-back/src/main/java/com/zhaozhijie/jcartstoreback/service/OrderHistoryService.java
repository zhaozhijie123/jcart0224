package com.zhaozhijie.jcartstoreback.service;

import com.zhaozhijie.jcartstoreback.po.OrderHistory;

import java.util.List;

public interface OrderHistoryService {

    List<OrderHistory> getByOrderId(Long orderId);

}
