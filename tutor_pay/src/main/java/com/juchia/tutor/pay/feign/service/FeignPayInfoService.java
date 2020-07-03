package com.juchia.tutor.pay.feign.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.api.pay.bo.PayInfoBO;
import com.juchia.tutor.pay.auth.service.alipay.AlipayService;
import com.juchia.tutor.pay.auth.service.alipay.entity.request.PayBizContent;
import com.juchia.tutor.pay.common.entity.po.PayInfo;
import com.juchia.tutor.pay.common.mapper.PayInfoMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeignPayInfoService extends ServiceImpl<PayInfoMapper, PayInfo> {

    @Autowired
    AlipayService alipayService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Transactional
    public String savePayInfo(PayInfoBO payInfoBO) {

        PayInfo payInfo = BeanCopyUtils.copyBean(payInfoBO, PayInfo.class);
        getBaseMapper().insert(payInfo);


        PayBizContent payBizContent = new PayBizContent();
        payBizContent.setOutTradeNo(payInfo.getTradeNo()); //订单号
        payBizContent.setTotalAmount(payInfo.getTotalAmount()); //金额
        payBizContent.setSubject(payInfo.getName()); //标题
        payBizContent.setBody(payInfo.getDescription());//描述
        payBizContent.setTimeoutExpress(payInfo.getTimeoutExpress());//订单超时表达式
//        String url=null;
//        try {
//            url = alipayService.pay(payBizContent);
//
////          创建延时队列消息, 15分钟后未付款则关闭订单
//            rabbitTemplate.convertAndSend(
//                    RabbitConfig.EXCHANGE_TTL, //TTL交换机
//                    RabbitConfig.ROUTINGKEY_TTL, //TTL路由键
//                    payInfoBO, //消息载体,
//                    message -> { //消息后处理器
//                        MessageProperties messageProperties = message.getMessageProperties();//消息配置属性
//                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT); // 消息持久化
//                        messageProperties.setExpiration(1*60*1000 + ""); // 消息TTL,单位毫秒
//                        return message;
//                    },
//                    new CorrelationData(UUID.randomUUID().toString()) //消息id
//            );
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
