package com.zhaozhijie.jcartadministrationback.dto.out;

import java.util.List;

public class OrderShowOutDTO {
    private Long orderId;
    private Integer customerId;
    private Byte status;
    private Double totalPrice;
    private Integer rewordPoints;
    private Long createTimestamp;
    private Long updateTimestamp;
    private Short shipMethod;
    private String shipAddress;
    private Double shipPrice;
    private Short payMethod;
    private String invoiceAddress;
    private Double invoicePrice;
    private String comment;
    private List<OrderProductShowOutDTO> orderProducts;

}
