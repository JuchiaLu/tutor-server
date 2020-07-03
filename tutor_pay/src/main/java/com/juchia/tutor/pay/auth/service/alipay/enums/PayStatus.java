package com.juchia.tutor.pay.auth.service.alipay.enums;

import lombok.Getter;

@Getter
public enum PayStatus {

    WAIT_BUYER_PAY("WAIT_BUYER_PAY","交易创建"),
    TRADE_CLOSED("TRADE_CLOSED","未付款交易超时关闭，或支付完成后全额退款"),
    TRADE_SUCCESS("TRADE_SUCCESS","交易支付成功"),
    TRADE_FINISHED("TRADE_FINISHED","交易结束");

    private String code;

    private String name;

    PayStatus(String code,String name){
        this.code = code;
        this.name = name;
    }
}
