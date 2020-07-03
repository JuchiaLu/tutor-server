package com.juchia.tutor.api.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CloseResponse implements Serializable {

    private AlipayTradeCloseResponse alipayTradeCloseResponse;

    @Data
    public class AlipayTradeCloseResponse {
        //    trade_no	    String	必填		支付宝交易号	20131120110010
        private String tradeNo;

        //    out_trade_no	String	必填		商家订单号	6823789339978248
        private String outTradeNo;


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
