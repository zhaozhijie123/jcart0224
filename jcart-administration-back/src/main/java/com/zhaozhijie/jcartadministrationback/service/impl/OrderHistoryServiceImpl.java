package com.zhaozhijie.jcartadministrationback.service.impl;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.dao.OrderHistoryMapper;
import com.zhaozhijie.jcartadministrationback.dto.out.OrderListOutDTO;
import com.zhaozhijie.jcartadministrationback.dto.out.OrderShowOutDTO;
import com.zhaozhijie.jcartadministrationback.po.Order;
import com.zhaozhijie.jcartadministrationback.po.OrderHistory;
import com.zhaozhijie.jcartadministrationback.service.OrderHistoryService;
import com.zhaozhijie.jcartadministrationback.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    @Autowired
    private OrderHistoryMapper orderHistoryMapper;

    @Autowired
    private OrderService orderService;

    @Override
    public List<OrderHistory> getByOrderId(Long orderId) {
        List<OrderHistory> orderHistories = orderHistoryMapper.selectByOrderId(orderId);
        return orderHistories;
    }

    @Override
    @Transactional
    public Long create(OrderHistory orderHistory) {
        orderHistoryMapper.insertSelective(orderHistory);
        Order order = new Order();
        order.setOrderId(orderHistory.getOrderId());
        order.setStatus(orderHistory.getOrderStatus());
        order.setUpdateTime(new Date());
        orderService.update(order);
        Long orderHistoryId = orderHistory.getOrderHistoryId();
        return orderHistoryId;
    }
}
