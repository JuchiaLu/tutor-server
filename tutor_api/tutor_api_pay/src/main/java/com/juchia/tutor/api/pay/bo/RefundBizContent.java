package com.juchia.tutor.api.pay.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RefundBizContent implements Serializable {

//    商家订单号 特殊可选
    private String outTradeNo;

//    支付宝订单号 特殊可选
    private String tradeNo;

//    退款金额 必选
    private BigDecimal refundAmount;

//    退款原因 可选
    private String refundReason;

//    本次退款标识号(一笔订单多次退款时填写, 每次保证不一样)
    private String outRequestNo;


}
