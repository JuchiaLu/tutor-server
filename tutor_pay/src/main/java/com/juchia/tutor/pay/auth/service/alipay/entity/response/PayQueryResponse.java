package com.juchia.tutor.pay.auth.service.alipay.entity.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PayQueryResponse implements Serializable {

//    alipay_trade_query_response 一般不主动查询, 用异步通知
    private AlipayTradeQueryResponse alipayTradeQueryResponse;

    @Data
    public static class AlipayTradeQueryResponse implements Serializable{
        //    trade_no	    String	必填		支付宝交易号	20131120110010
        private String tradeNo;

//    out_trade_no	String	必填		商家订单号	6823789339978248
        private String outTradeNo;

//    buyer_logon_id	String	必填	买家支付宝账号	159****5620
        private String buyerLogonId;

//    trade_status	String	必填		交易状态
        private String tradeStatus;

//    total_amount	Price	必填		交易的订单金额，单位为元，两位小数
        private BigDecimal totalAmount;


//        以下是公共参数
//       "code": "20000",
//       "msg": "Service Currently Unavailable",
//       "sub_code": "isp.unknow-error",
//       "sub_msg": "系统繁忙"
        private String code;

        private String msg;

        private String subCode;

        private String subMsg;



    }

//    签名
    private String sign;
}
