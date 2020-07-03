package com.juchia.tutor.pay.auth.service.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.juchia.tutor.pay.auth.service.alipay.config.AlipayConfig;
import com.juchia.tutor.pay.auth.service.alipay.config.RabbitConfig;
import com.juchia.tutor.pay.auth.service.alipay.entity.request.*;
import com.juchia.tutor.pay.auth.service.alipay.entity.response.*;
import com.juchia.tutor.pay.common.entity.po.PayInfo;
import com.juchia.tutor.pay.common.exception.BusinessException;
import com.juchia.tutor.pay.common.mapper.PayInfoMapper;
import com.juchia.tutor.pay.common.properties.PayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class AlipayService {

    private final ObjectMapper mapper = new ObjectMapper();

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    PayInfoMapper payInfoMapper;

    @Autowired
    private PayProperties payProperties;

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
        String result = null;
        try {
            result = alipayClient.sdkExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("参数错误");
        }
        return payProperties.getAliPay().getGatewayUrl() + "?" + result;
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
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("参数错误");
        }
//       Json 转 对象 且下滑线转驼峰
        PayQueryResponse payQueryBizContentResponse = mapper.readValue(result, PayQueryResponse.class);
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
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("参数错误");
        }

        //       Json 转 对象 且下滑线转驼峰
        CloseResponse payCloseBizContentResponse = mapper.readValue(result, CloseResponse.class);
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
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            throw new BusinessException("参数错误");
        }

        //       Json 转 对象 且下滑线转驼峰
        RefundResponse refundBizContentResponse = mapper.readValue(result, RefundResponse.class);
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
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("参数错误");
        }

        RefundQueryResponse refundQueryBizContentResponse = mapper.readValue(result, RefundQueryResponse.class);
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
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("参数错误");
        }

        TransferResponse transferResponse = mapper.readValue(result, TransferResponse.class);
        return transferResponse;
    }

    /**
     * 异步通知
     */
    public String asyncNotify(HttpServletRequest request)throws UnsupportedEncodingException{
        /* *
         * 功能：支付宝服务器异步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
         * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
         * 如果没有收到该页面返回的 success
         * 建议该页面只做支付成功的业务逻辑处理，退款的处理请以调用退款查询接口的结果为准。
         */

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>(); //参数
        Map<String,String[]> requestParams = request.getParameterMap(); //请求参数

//        遍历请求参数
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, payProperties.getAliPay().getAlipayPublicKey(), payProperties.getAliPay().getCharset(), payProperties.getAliPay().getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("验签失败");
        }

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

//                根据订单id查找appointId
//                PayInfo condition = new PayInfo();
//                condition.setTradeNo(out_trade_no);
//                PayInfo payInfo = payInfoMapper.selectOne(new QueryWrapper<>(condition));
//
//                PayInfoBO payInfoBO = BeanCopyUtils.copyBean(payInfo,PayInfoBO.class);

                com.juchia.tutor.api.pay.bo.PayBizContent payBizContent = new com.juchia.tutor.api.pay.bo.PayBizContent();
                payBizContent.setOutTradeNo(out_trade_no);


                //发布支付成功消息
                rabbitTemplate.convertAndSend(
                        RabbitConfig.EXCHANGE_PAY_SUCCESS, //交换机名
                        RabbitConfig.ROUTINGKEY_PAY_SUCCESS, //路由键
                        payBizContent, //消息载体, 返回 appointId
                        new CorrelationData(UUID.randomUUID().toString()) //消息id
                );
            }else if (trade_status.equals("TRADE_CLOSED")){
//                根据订单id查找appointId
                PayInfo condition = new PayInfo();
                condition.setTradeNo(out_trade_no);
                PayInfo payInfo = payInfoMapper.selectOne(new QueryWrapper<>(condition));
                payInfo.setState(4);
                payInfoMapper.updateById(payInfo);
            }
            System.out.println("success");
            return "success";

        }else {//验证失败
            System.out.println("fail");
            return "fail";

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

        //——请在这里编写您的程序（以上代码仅作参考）——

    }

    /**
     * 同步通知
     */
    public void syncReturn(HttpServletRequest request)throws UnsupportedEncodingException{

        /* *
         * 功能：支付宝服务器同步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
         */

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, payProperties.getAliPay().getAlipayPublicKey(), payProperties.getAliPay().getCharset(), payProperties.getAliPay().getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException("验签失败");
        }

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {

//            TODO 采用消息队列 发送支付成功消息
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);

            // 前台展示支付成功
        }else {
            System.out.println("验签失败");
        }
        //——请在这里编写您的程序（以上代码仅作参考）——

    }




//    @RabbitHandler // 声明接收方法
//    @RabbitListener(queues = {RabbitConfig.QUEUE_DLX,RabbitConfig.QUEUE_PAY_SUCCESS}) //声明监听死信队列
//    public void processDLX(Message message, Channel channel) {
//        String payload = new String(message.getBody()); //也可以直接用 @Payload 注解直接获取body
//        log.info("接收处理DLX队列当中的消息: {}, 当前时间: {}", payload, LocalDateTime.now().toString());
//        try {
////              消息的标识，false只确认当前一个消息收到，true确认consumer获得的所有消息
////              channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
////              ack返回false，并重新回到队列
////              channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
////              拒绝消息
////              channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
////
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (IOException e) {
//            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
//            // 处理消息失败，将消息重新放回队列
//            try {
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//    }

}
