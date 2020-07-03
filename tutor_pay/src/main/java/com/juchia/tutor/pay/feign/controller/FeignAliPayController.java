package com.juchia.tutor.pay.feign.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juchia.tutor.api.pay.bo.*;
import com.juchia.tutor.api.pay.client.IFeignAliPay;
import com.juchia.tutor.api.pay.vo.*;
import com.juchia.tutor.pay.feign.service.FeignAlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Validated
@RestController
@RequestMapping("feign/alipay") //TODO 服务间调用是否经过网关?
@Slf4j
public class FeignAliPayController implements IFeignAliPay {

    @Autowired
    FeignAlipayService feignAlipayService;


    /**
     * 付款
     */
    @PostMapping("pay")  //TODO 用get请求时不能用, 原因是有 body 时自动转换成POST请求
    @Override
    public String pay(@RequestBody PayBizContent payBizContent) throws UnsupportedEncodingException, JsonProcessingException {
        return feignAlipayService.pay(payBizContent);
    }

    /**
     * 交易查询
     */
    @PostMapping("payQuery")
    @Override
    public PayQueryResponse payQuery(@RequestBody PayQueryBizContent payQueryBizContent) throws IOException {
        return feignAlipayService.payQuery(payQueryBizContent);
    }


    /**
     * 关闭订单 未付款前可以关闭交易
     * @param
     */
    @PostMapping("close")
    @Override
    public CloseResponse close(@RequestBody CloseBizContent closeBizContent) throws IOException {
        return feignAlipayService.close(closeBizContent);
    }


    /**
     * 退款 退款日期未达到前可以退款
     * @param
     */
    @PostMapping("refund")
    @Override
    public RefundResponse refund(@RequestBody RefundBizContent refundBizContent) throws IOException {
        return feignAlipayService.refund(refundBizContent);
    }

    /**
     * 退款查询
     * @param
     */
    @PostMapping("refundQuery")
    @Override
    public RefundQueryResponse refundQuery(@RequestBody RefundQueryBizContent refundQueryBizContent) throws IOException {

        return feignAlipayService.refundQuery(refundQueryBizContent);

    }


    /**
     * 转账
     * @param
     */
    @GetMapping("/transfer")
    public TransferResponse transfer(TransferBizContent transferBizContent) throws IOException {
        return feignAlipayService.transfer(transferBizContent);

    }
}
