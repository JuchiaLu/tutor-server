package com.juchia.tutor.pay.auth.service.alipay.entity.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RefundQueryBizContent implements Serializable {

//    商家订单号
    private String outTradeNo;

//    支付宝订单号
    private String tradeNo;

//    本次退款标识号(一笔订单多次退款时填写, 每次保证不一样,即使未真正退款(如第二次退款金额总和大于总金额))
    private String outRequestNo;


}
