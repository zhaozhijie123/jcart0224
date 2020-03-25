package com.zhaozhijie.jcartstoreback.kafka;

import com.alibaba.fastjson.JSONObject;
import com.zhaozhijie.jcartstoreback.dao.ProductOperationMapper;
import com.zhaozhijie.jcartstoreback.mq.HotProductDTO;
import com.zhaozhijie.jcartstoreback.po.ProductOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HotProductConsumer {
    @Autowired
    private ProductOperationMapper productOperationMapper;
    @KafkaListener(topics = "test")
    public void onMessage(String stringHotProductDTOConsumerRecord) {
        //把接收的字符串转化为实体类
        HotProductDTO hotProductDTO = JSONObject.parseObject(stringHotProductDTOConsumerRecord, HotProductDTO.class);
        Integer productId = hotProductDTO.getProductId();
        ProductOperation productOperation = productOperationMapper.selectByPrimaryKey(productId);
        if (productOperation == null){
            productOperation = new ProductOperation();
            productOperation.setProductId(productId);
            productOperation.setAllCount(1);
            productOperation.setDayCount(1);
            productOperation.setRecentTime(new Date());
            productOperationMapper.insertSelective(productOperation);
        }else {
            productOperation.setAllCount(productOperation.getAllCount()+1);
            productOperation.setDayCount(productOperation.getDayCount()+1);
            productOperation.setRecentTime(new Date());
            productOperationMapper.updateByPrimaryKeySelective(productOperation);
        }
    }
}
