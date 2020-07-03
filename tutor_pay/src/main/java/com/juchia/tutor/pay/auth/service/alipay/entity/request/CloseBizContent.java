package com.juchia.tutor.pay.auth.service.alipay.entity.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CloseBizContent implements Serializable {

//    out_trade_no 外部订单号 特殊可选
    private String outTradeNo;

//    trade_no 支付宝交易号 特殊可选
    private String tradeNo;

}
