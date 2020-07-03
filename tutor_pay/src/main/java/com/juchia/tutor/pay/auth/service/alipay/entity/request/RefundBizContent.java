package com.juchia.tutor.pay.auth.service.alipay.entity.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RefundBizContent implements Serializable {

//    商家订单号
    private String outTradeNo;

//    支付宝订单号
    private String tradeNo;

//    退款金额
    private BigDecimal refundAmount;

//    退款原因
    private String refundReason;

//    本次退款标识号(一笔订单多次退款时填写, 每次保证不一样)
    private String outRequestNo;


}
