package com.juchia.tutor.api.pay.client;

import com.juchia.tutor.api.pay.bo.*;
import com.juchia.tutor.api.pay.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("pay/feign/alipay")
public interface IFeignAliPay {

    /**
     * 付款
     */
    @PostMapping("pay")
    String pay(@RequestBody PayBizContent payBizContent) throws Exception;

    /**
     * 交易查询
     */
    @PostMapping("payQuery")
    PayQueryResponse payQuery(@RequestBody PayQueryBizContent payQueryBizContent) throws Exception;


    /**
     * 关闭订单 未付款前可以关闭交易
     * @param
     */
    @PostMapping("close")
    CloseResponse close(@RequestBody CloseBizContent closeBizContent) throws Exception;


    /**
     * 退款 退款日期未达到前可以退款
     * @param
     */
    @PostMapping("refund")
    RefundResponse refund(@RequestBody RefundBizContent refundBizContent) throws Exception;

    /**
     * 退款查询
     * @param
     */
    @PostMapping("refundQuery")
    RefundQueryResponse refundQuery(@RequestBody RefundQueryBizContent refundQueryBizContent) throws Exception;


    /**
     * 转账
     * @param
     */
    @GetMapping("/transfer")
    TransferResponse transfer(TransferBizContent transferBizContent) throws Exception ;

}
