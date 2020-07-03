package com.juchia.tutor.pay.auth.service.alipay.entity.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RefundQueryResponse implements Serializable {

    private AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse;

    @Data
    public static class AlipayTradeFastpayRefundQueryResponse implements Serializable{ //    商家订单号

        private String outTradeNo;

        //    支付宝订单号
        private String tradeNo;

        //    该笔退款所对应的交易的订单金额
        private BigDecimal totalAmount;

        //    退款金额
        private BigDecimal refundAmount;

        //    退款原因
        private String refundReason;

        //    本次退款标识号(一笔订单多次退款时填写, 每次保证不一样)
        private String outRequestNo;


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
