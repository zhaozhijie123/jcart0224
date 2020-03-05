package com.zhaozhijie.jcartstoreback.service.impl;

import com.zhaozhijie.jcartstoreback.dao.OrderHistoryMapper;
import com.zhaozhijie.jcartstoreback.po.OrderHistory;
import com.zhaozhijie.jcartstoreback.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    @Autowired
    private OrderHistoryMapper orderHistoryMapper;

    @Override
    public List<OrderHistory> getByOrderId(Long orderId) {
        List<OrderHistory> orderHistories = orderHistoryMapper.selectByOrderId(orderId);
        return orderHistories;
    }
}
