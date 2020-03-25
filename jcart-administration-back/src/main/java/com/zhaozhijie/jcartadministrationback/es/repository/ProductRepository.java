package com.zhaozhijie.jcartadministrationback.es.repository;

import com.zhaozhijie.jcartadministrationback.es.doc.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<ProductDoc,Integer> {
}
