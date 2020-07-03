package com.juchia.tutor.pay.auth.service.alipay.entity.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransferResponse implements Serializable {


    private AlipayFundTransUniTransferResponse alipayFundTransUniTransferResponse;

    @Data
     public static class AlipayFundTransUniTransferResponse implements Serializable{

        private String outBizNo	;//String	必填	64	商户订单号	201808080001

        private String orderId;	//String	必填	32	支付宝转账订单号	20190801110070000006380000250621

        private String payFundOrderId	;//String	选填	32	支付宝支付资金流水号	20190801110070001506380000251556

        private String status;	//String	选填	32	转账单据状态。
        //SUCCESS：成功（对转账到银行卡的单据, 该状态可能变为退票[REFUND]状态）；
        //FAIL：失败（具体失败原因请参见error_code以及fail_reason返回值）；
        //DEALING：处理中；
        //REFUND：退票；	SUCCESS

        private String transDate;	//String	必填	30	订单支付时间，格式为yyyy-MM-dd HH:mm:ss	2019-08-21 00:00:00


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
