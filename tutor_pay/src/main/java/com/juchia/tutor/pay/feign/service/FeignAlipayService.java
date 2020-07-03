package com.juchia.tutor.pay.feign.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.juchia.tutor.api.pay.bo.*;
import com.juchia.tutor.api.pay.vo.*;
import com.juchia.tutor.pay.auth.service.alipay.config.RabbitConfig;
import com.juchia.tutor.pay.common.exception.BusinessException;
import com.juchia.tutor.pay.common.properties.PayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
@Slf4j
public class FeignAlipayService{

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private PayProperties payProperties;

    {
//      小驼峰转下划线
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//      忽略 null 和 空
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//      忽略 没有的 属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//       日期
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 付款
     * 返回 跳转地址
     */
    public String pay(PayBizContent payBizContent) throws UnsupportedEncodingException, JsonProcessingException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(payProperties.getAliPay().getGatewayUrl(), payProperties.getAliPay().getAppId(), payProperties.getAliPay().getMerchantPrivateKey(), "json", payProperties.getAliPay().getCharset(), payProperties.getAliPay().getAlipayPublicKey(), payProperties.getAliPay().getSignType());

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(payProperties.getAliPay().getReturnUrl());
        alipayRequest.setNotifyUrl(payProperties.getAliPay().getNotifyUrl());

        alipayRequest.setBizContent(mapper.writeValueAsString(payBizContent));

        //请求
        AlipayTradePagePayResponse alipayTradePagePayResponse;
        try {
             alipayTradePagePayResponse = alipayClient.sdkExecute(alipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("配置错误");
        }
//        if(!alipayTradePagePayResponse.getCode().equals("10000")){
//            throw new BusinessException(alipayTradePagePayResponse.getSubMsg());
//        }

        //          创建延时队列消息, 15分钟后未付款则关闭订单
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_TTL, //TTL交换机
                RabbitConfig.ROUTINGKEY_TTL, //TTL路由键
                payBizContent, //消息载体,
                message -> { //消息后处理器
                    MessageProperties messageProperties = message.getMessageProperties();//消息配置属性
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT); // 消息持久化
                    messageProperties.setExpiration(15*60*1000 + ""); // 消息TTL,单位毫秒
                    return message;
                },
                new CorrelationData(UUID.randomUUID().toString()) //消息id
        );


        return payProperties.getAliPay().getGatewayUrl() + "?" + alipayTradePagePayResponse.getBody(); //TODO 不直接返回url
    }

    /**
     * 交易查询
     */
    public PayQueryResponse payQuery(PayQueryBizContent payQueryBizContent) throws IOException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(payProperties.getAliPay().getGatewayUrl(), payProperties.getAliPay().getAppId(), payProperties.getAliPay().getMerchantPrivateKey(), "json", payProperties.getAliPay().getCharset(), payProperties.getAliPay().getAlipayPublicKey(), payProperties.getAliPay().getSignType());

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        alipayRequest.setBizContent(mapper.writeValueAsString(payQueryBizContent)); //Json 且转 小驼峰

        //请求
        AlipayTradeQueryResponse alipayTradeQueryResponse;
        try {
            alipayTradeQueryResponse = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("配置错误");
        }
        if(!alipayTradeQueryResponse.getCode().equals("10000")){
            throw new BusinessException(alipayTradeQueryResponse.getSubMsg());
        }
//       Json 转 对象 且下滑线转驼峰
        PayQueryResponse payQueryBizContentResponse = mapper.readValue(alipayTradeQueryResponse.getBody(), PayQueryResponse.class);
        return payQueryBizContentResponse;
    }


    /**
     * 关闭订单 未付款前可以关闭交易
     * @param
     */
    public CloseResponse close(CloseBizContent closeBizContent) throws IOException {

        AlipayClient alipayClient = new DefaultAlipayClient(payProperties.getAliPay().getGatewayUrl(), payProperties.getAliPay().getAppId(), payProperties.getAliPay().getMerchantPrivateKey(), "json", payProperties.getAliPay().getCharset(), payProperties.getAliPay().getAlipayPublicKey(), payProperties.getAliPay().getSignType());

        //设置请求参数
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();


        alipayRequest.setBizContent(mapper.writeValueAsString(closeBizContent)); //Json 且转 小驼峰

        //请求
        AlipayTradeCloseResponse alipayTradeCloseResponse;
        try {
             alipayTradeCloseResponse = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("配置错误");
        }
        if(!alipayTradeCloseResponse.getCode().equals("10000")){
            throw new BusinessException(alipayTradeCloseResponse.getSubMsg());
        }

        //       Json 转 对象 且下滑线转驼峰
        CloseResponse payCloseBizContentResponse = mapper.readValue(alipayTradeCloseResponse.getBody(), CloseResponse.class);
        return payCloseBizContentResponse;
    }


    /**
     * 退款 退款日期未达到前可以退款
     * @param
     */
    public RefundResponse refund(RefundBizContent refundBizContent) throws IOException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(payProperties.getAliPay().getGatewayUrl(), payProperties.getAliPay().getAppId(), payProperties.getAliPay().getMerchantPrivateKey(), "json", payProperties.getAliPay().getCharset(), payProperties.getAliPay().getAlipayPublicKey(), payProperties.getAliPay().getSignType());

        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        alipayRequest.setBizContent(mapper.writeValueAsString(refundBizContent));

        //请求
        AlipayTradeRefundResponse alipayTradeRefundResponse;
        try {
             alipayTradeRefundResponse = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            throw new BusinessException("配置错误");
        }
        if(!alipayTradeRefundResponse.getCode().equals("10000")){
            throw new BusinessException(alipayTradeRefundResponse.getSubMsg());
        }

        //       Json 转 对象 且下滑线转驼峰
        RefundResponse refundBizContentResponse = mapper.readValue(alipayTradeRefundResponse.getBody(), RefundResponse.class);
        return refundBizContentResponse;
    }

    /**
     * 退款查询
     * @param
     */
    public RefundQueryResponse refundQuery(RefundQueryBizContent refundQueryBizContent) throws IOException {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(payProperties.getAliPay().getGatewayUrl(), payProperties.getAliPay().getAppId(), payProperties.getAliPay().getMerchantPrivateKey(), "json", payProperties.getAliPay().getCharset(), payProperties.getAliPay().getAlipayPublicKey(), payProperties.getAliPay().getSignType());

        //设置请求参数
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

        alipayRequest.setBizContent(mapper.writeValueAsString(refundQueryBizContent));

        //请求
        AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse;
        try {
            alipayTradeFastpayRefundQueryResponse = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("配置错误");
        }
        if(!alipayTradeFastpayRefundQueryResponse.getCode().equals("10000")){
            throw new BusinessException(alipayTradeFastpayRefundQueryResponse.getSubMsg());
        }

        RefundQueryResponse refundQueryBizContentResponse = mapper.readValue(alipayTradeFastpayRefundQueryResponse.getBody(), RefundQueryResponse.class);
        return refundQueryBizContentResponse;
    }



    /**
     * 转账
     * @param transferBizContent
     * @return
     * @throws IOException
     */
    public TransferResponse transfer(TransferBizContent transferBizContent)throws IOException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(payProperties.getAliPay().getGatewayUrl(), payProperties.getAliPay().getAppId(), payProperties.getAliPay().getMerchantPrivateKey(), "json", payProperties.getAliPay().getCharset(), payProperties.getAliPay().getAlipayPublicKey(), payProperties.getAliPay().getSignType());

        //设置请求参数
        AlipayFundTransUniTransferRequest  alipayRequest = new AlipayFundTransUniTransferRequest ();

        alipayRequest.setBizContent(mapper.writeValueAsString(transferBizContent));

        //请求
        AlipayFundTransUniTransferResponse alipayFundTransUniTransferResponse;
        try {
            alipayFundTransUniTransferResponse = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("配置错误");
        }
        if(!alipayFundTransUniTransferResponse.getCode().equals("10000")){
            throw new BusinessException(alipayFundTransUniTransferResponse.getSubMsg());
        }

        TransferResponse transferResponse = mapper.readValue(alipayFundTransUniTransferResponse.getBody(), TransferResponse.class);
        return transferResponse;
    }
}
