package com.zhaozhijie.jcartadministrationback.dao;

import com.zhaozhijie.jcartadministrationback.po.ReturnHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnHistoryMapper {
    int deleteByPrimaryKey(Long returnHistoryId);

    int insert(ReturnHistory record);

    int insertSelective(ReturnHistory record);

    ReturnHistory selectByPrimaryKey(Long returnHistoryId);

    int updateByPrimaryKeySelective(ReturnHistory record);

    int updateByPrimaryKey(ReturnHistory record);
}