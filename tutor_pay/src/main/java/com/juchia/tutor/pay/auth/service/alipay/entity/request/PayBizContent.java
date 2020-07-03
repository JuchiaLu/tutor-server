package com.juchia.tutor.pay.auth.service.alipay.entity.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// 下单并支付
@Data
public class PayBizContent implements Serializable {

//    out_trade_no 必选 商户订单号
    private String outTradeNo;

//    product_code 必选 销售产品码 固定值 FAST_INSTANT_TRADE_PAY
    private String productCode = "FAST_INSTANT_TRADE_PAY";

//    total_amount 必选 金额 两位小数
    private BigDecimal totalAmount;

//    subject 必选 订单标题
    private String subject;

//    body 可选 订单描述
    private String body;

//    time_expire 可选 订单绝对超时时间 格式为yyyy-MM-dd HH:mm:ss	2016-12-31 10:05:01
    private LocalDateTime timeExpire;

//    timeout_express 可选 订单超时时间表达式 1m～15d。m-分钟，h-小时，d-天，1c-当天
    private String timeoutExpress;


}
