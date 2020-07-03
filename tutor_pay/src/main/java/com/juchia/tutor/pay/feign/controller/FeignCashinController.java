package com.juchia.tutor.pay.feign.controller;

import com.juchia.tutor.api.pay.bo.CashinBO;
import com.juchia.tutor.api.pay.client.IFeignCashin;
import com.juchia.tutor.api.pay.vo.CashinVO;
import com.juchia.tutor.pay.common.entity.po.Cashin;
import com.juchia.tutor.pay.feign.service.FeignCashinService;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/feign/cashins") //TODO 服务间调用是否经过网关?
@Slf4j
public class FeignCashinController implements IFeignCashin {

    @Autowired
    FeignCashinService feignCashinService;

    @Override
    @PostMapping
    public CashinVO saveCashin(CashinBO cashinBO) {
        Cashin cashin = feignCashinService.saveCashin(cashinBO);
        return BeanCopyUtils.copyBean(cashin,CashinVO.class);
    }
}
