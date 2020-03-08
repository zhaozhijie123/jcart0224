package com.zhaozhijie.jcartstoreback.service.impl;

import com.zhaozhijie.jcartstoreback.dao.ReturnHistoryMapper;
import com.zhaozhijie.jcartstoreback.po.ReturnHistory;
import com.zhaozhijie.jcartstoreback.service.ReturnHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnHistoryServiceImpl implements ReturnHistoryService {

    @Autowired
    private ReturnHistoryMapper returnHistoryMapper;

    @Override
    public List<ReturnHistory> getByReturnId(Integer returnId) {
        List<ReturnHistory> returnHistories = returnHistoryMapper.selectByReturnId(returnId);
        return returnHistories;
    }
}
