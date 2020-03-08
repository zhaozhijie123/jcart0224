package com.zhaozhijie.jcartadministrationback.service;

import com.github.pagehelper.Page;
import com.zhaozhijie.jcartadministrationback.po.Return;

public interface ReturnService {
    Page<Return> search(Integer pageNum);
}
