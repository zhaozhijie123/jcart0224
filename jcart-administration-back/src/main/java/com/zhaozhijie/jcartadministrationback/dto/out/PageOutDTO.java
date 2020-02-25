package com.zhaozhijie.jcartadministrationback.dto.out;

import java.util.List;

public class PageOutDTO<T> {
    private Integer total;
    private Integer pageSize;
    private Integer pageNum;
    private List<T> list;
}
