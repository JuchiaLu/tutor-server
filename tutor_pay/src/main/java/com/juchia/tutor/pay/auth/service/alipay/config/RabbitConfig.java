package com.juchia.tutor.pay.auth.service.alipay.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class RabbitConfig {
    /**
     * 备份交换机
     */
    public static final String EXCHANGE_AE = "EXCHANGE_AE";
    /**
     * TTL交换机
     */
    public static final String EXCHANGE_TTL = "EXCHANGE_TTL";
    /**
     * 死信交换机
     */
    public static final String EXCHANGE_DLX = "EXCHANGE_DLX";

    /**
     * 订单支付成功交换机
     */
    public static final String EXCHANGE_PAY_SUCCESS = "EXCHANGE_PAY_SUCCESS";

//    ---------------------------------------------------------------------------------

    /**
     * 备份队列
     */
    public static final String QUEUE_AE = "QUEUE_AE";
    /**
     * TTL队列 (15关闭订单)
     */
    public static final String QUEUE_TTL = "QUEUE_TTL";
    /**
     * 死信队列
     */
    public static final String QUEUE_DLX = "QUEUE_DLX";
    /**
     * 支付成功队列
     */
    public static final String QUEUE_PAY_SUCCESS = "QUEUE_PAY_SUCCESS";


//    ---------------------------------------------------------------------------------------

    /**
     * TTL Routing_Key 路由键
     * 特殊字符“*”与“#”，用于做模糊匹配，其中“*”用于匹配一个单词，“#”用于匹配多个单词（可以是零个）
     */
    public static final String ROUTINGKEY_TTL = "routing.ttl.#";
    /**
     * 死信 Routing_Key 路由键
     */
    public static final String ROUTINGKEY_DLX = "routing.dlx";

    /**
     * 支付成功 Routing_Key 路由键
     */
    public static final String ROUTINGKEY_PAY_SUCCESS = "routing.pay.success";

//    -----------------------------------------------------------------------------------------

    /**
     * 备份队列交换机 使用广播型交换机
     */
    @Bean
    public FanoutExchange alternateExchange() {
        return new FanoutExchange(EXCHANGE_AE);
    }

    /**
     * TTL队列交换机, 指定备份的交换机, 使用多播交换机
     */
    @Bean
    public TopicExchange ttlExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("alternate-exchange", EXCHANGE_AE);
         //durable="true" 持久化消息队列 ， rabbitmq重启的时候不需要创建新的队列
         //auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         //exclusive  表示该消息队列是否只在当前connection生效,默认是false
        return new TopicExchange(EXCHANGE_TTL, true, false, args);
    }

    /**
     * 死信队列交换机, 使用单播交换机
     */
    @Bean
    public DirectExchange deathLetterExchange() {
        return new DirectExchange(EXCHANGE_DLX);
    }


    /**
     * 订单支付成功队列交换机, 使用单播交换机
     */
    @Bean
    public DirectExchange paySuccessExchange() {
        return new DirectExchange(EXCHANGE_PAY_SUCCESS);
    }

//---------------------------------------------------------------------------------

    /**
     * 备份队列
     */
    @Bean
    public Queue queueAE() {
//        return new Queue(QUEUE_AE, true); //队列持久
        return QueueBuilder.durable(QUEUE_AE).build();
    }

    /**
     * TTL队列,指定死信交换机
     */
    @Bean
    public Queue queueTTL() {
        Map<String, Object> args = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        args.put("x-dead-letter-exchange", EXCHANGE_DLX); //死信转发到死信交换机
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        args.put("x-dead-letter-routing-key", ROUTINGKEY_DLX);//转发到死信交换机时携带的路由键名
//        return new Queue(QUEUE_TTL, true, false, false, args);
        return QueueBuilder.durable(QUEUE_TTL).withArguments(args).build();
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue queueDLX() {
//        return new Queue(QUEUE_DLX, true); //队列持久
        return QueueBuilder.durable(QUEUE_DLX).build();
    }


    /**
     * 死信队列
     */
    @Bean
    public Queue queuePaySuccess() {
//        return new Queue(QUEUE_DLX, true); //队列持久
        return QueueBuilder.durable(QUEUE_PAY_SUCCESS).build();
    }

//    -------------------------------------------------------------------------

    /**
     * 备份队列 绑定到 备份交换机
     */
    @Bean
    public Binding bindingAE() {
        return BindingBuilder.bind(queueAE()).to(alternateExchange());
    }

    /**
     * TTL队列 绑定到TTL交换机
     */
    @Bean
    public Binding bindingTTL() {
        return BindingBuilder.bind(queueTTL()).to(ttlExchange()).with(ROUTINGKEY_TTL);
    }

    /**
     * 死信队列 绑定到 死信交换机
     */
    @Bean
    public Binding bindingDLX() {
        return BindingBuilder.bind(queueDLX()).to(deathLetterExchange()).with(ROUTINGKEY_DLX);
    }

    /**
     * 支付成功队列 绑定到 支付成功交换机
     */
    @Bean
    public Binding bindingPaySuccess() {
        return BindingBuilder.bind(queuePaySuccess()).to(paySuccessExchange()).with(ROUTINGKEY_PAY_SUCCESS);
    }

}
