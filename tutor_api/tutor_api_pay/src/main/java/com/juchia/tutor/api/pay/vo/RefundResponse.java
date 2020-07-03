package com.juchia.tutor.api.pay.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class RefundResponse implements Serializable {


    private AlipayTradeRefundResponse alipayTradeRefundResponse;

    @Data
     public static class AlipayTradeRefundResponse{

         //    out_trade_no	String	必填	64	商户订单号	6823789339978248
         private String outTradeNo;

         //    trade_no	String	必填	64	2013112011001004330000121536	支付宝交易号
         private String tradeNo;

         //    buyer_logon_id	String	必填	100	用户的登录id	159****5620
         private String buyerLogonId;

         //    fund_change	String	必填	1	本次退款是否发生了资金变化	Y 或 N
         private String fundChange;

         //    refund_fee	Price	必填	11	退款总金额	88.88
         private BigDecimal refundFee;

         //    gmt_refund_pay	Date	必填	32	退款支付时间	2014-11-27 15:45:57
         private Date gmtRefundPay;


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
