package com.juchia.tutor.system.auth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.system.auth.entity.bo.AdviceBO1;
import com.juchia.tutor.system.common.entity.po.Advice;
import com.juchia.tutor.system.common.mapper.AdviceMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthAdviceService extends ServiceImpl<AdviceMapper, Advice> {


    public Advice mySave(Long id, AdviceBO1 adviceBO) {
        Advice advice = BeanCopyUtils.copyBean(adviceBO, Advice.class);
        getBaseMapper().insert(advice);
        return advice;
    }

}
