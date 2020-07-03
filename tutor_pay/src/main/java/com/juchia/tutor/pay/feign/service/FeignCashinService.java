package com.juchia.tutor.pay.feign.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.api.pay.bo.CashinBO;
import com.juchia.tutor.pay.auth.service.alipay.AlipayService;
import com.juchia.tutor.pay.common.entity.po.Cashin;
import com.juchia.tutor.pay.common.mapper.CashinMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeignCashinService extends ServiceImpl<CashinMapper, Cashin> {

    @Autowired
    AlipayService alipayService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public Cashin saveCashin(CashinBO cashinBO) {
        Cashin cashin = BeanCopyUtils.copyBean(cashinBO, Cashin.class);
        getBaseMapper().insert(cashin);
        return cashin;
    }
}
