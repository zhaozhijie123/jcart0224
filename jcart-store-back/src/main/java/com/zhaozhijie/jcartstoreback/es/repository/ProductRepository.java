package com.zhaozhijie.jcartstoreback.es.repository;

import com.zhaozhijie.jcartstoreback.es.doc.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<ProductDoc,Integer> {
    List<ProductDoc> findByProductNameLikeOrProductAbstractLike(String keyword, String keyword1);
}
