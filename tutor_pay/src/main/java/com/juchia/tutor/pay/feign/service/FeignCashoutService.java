package com.juchia.tutor.pay.feign.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.api.pay.bo.CashoutBO;
import com.juchia.tutor.pay.auth.service.alipay.AlipayService;
import com.juchia.tutor.pay.common.entity.po.Cashout;
import com.juchia.tutor.pay.common.mapper.CashoutMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeignCashoutService extends ServiceImpl<CashoutMapper, Cashout> {

    @Autowired
    AlipayService alipayService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public Cashout saveCashout(CashoutBO cashoutBO) {
        Cashout cashout = BeanCopyUtils.copyBean(cashoutBO, Cashout.class);
        getBaseMapper().insert(cashout);
        return cashout;
    }
}
