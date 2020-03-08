package com.zhaozhijie.jcartstoreback.service;

import com.zhaozhijie.jcartstoreback.po.ReturnHistory;

import java.util.List;

public interface ReturnHistoryService {
    List<ReturnHistory> getByReturnId(Integer returnId);
}
