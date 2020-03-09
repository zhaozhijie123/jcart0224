package com.zhaozhijie.jcartadministrationback.service;

import com.zhaozhijie.jcartadministrationback.po.ReturnHistory;

import java.util.List;

public interface ReturnHistoryService {
    List<ReturnHistory> getListByReturnId(Integer returnId);

    Long create(ReturnHistory returnHistory);
}
