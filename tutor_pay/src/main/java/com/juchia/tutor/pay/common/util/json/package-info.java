package com.juchia.tutor.pay.common.util.json;

// 这个工具包仅供测试，json 过滤的工具类，还可以使用 JsonView 注解

// 关于handler无法使用的原因
//引用来自“steven”的评论
//@DiamondFsd 我没有用spring-boot 使用的传统的方式xml配置方式：
//
//<mvc:annotation-driven>
//<mvc:message-converters>
//<ref bean="jsonMessageConverter"/>
//</mvc:message-converters>
//<mvc:return-value-handlers>
//<bean class="com.sunray.yunlong.base.json.JsonReturnHandler"></bean>
//</mvc:return-value-handlers>
//</mvc:annotation-driven>
//
//当Controller是使用@Controller注解时候，Handler才会进入执行， 如果是@RestController Handler才不会进入执行， 望解答 !!!
//因为 @RestController 会在Controller上加一个 @ResponseBody 注解 @ResponseBody注解有一个预置的Handler，这个Handler的排序在我们自定义Handler之前，所以会直接执行预置的Handler。
//
//你可以看一下我的源码，我是实现了BeanPostProcessor 这个接口，并且将我们自定义的Handler排序到第一位，所以可以处理