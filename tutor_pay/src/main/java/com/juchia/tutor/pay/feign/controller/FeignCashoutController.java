package com.juchia.tutor.pay.feign.controller;

import com.juchia.tutor.api.pay.bo.CashoutBO;
import com.juchia.tutor.api.pay.client.IFeignCashout;
import com.juchia.tutor.api.pay.vo.CashoutVO;
import com.juchia.tutor.pay.common.entity.po.Cashout;
import com.juchia.tutor.pay.feign.service.FeignCashoutService;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/feign/cashouts") //TODO 服务间调用是否经过网关?
@Slf4j
public class FeignCashoutController implements IFeignCashout {

    @Autowired
    FeignCashoutService feignCashoutService;

    @Override
    @PostMapping
    public CashoutVO saveCashout(CashoutBO cashoutBO) {
        Cashout cashout = feignCashoutService.saveCashout(cashoutBO);
        return BeanCopyUtils.copyBean(cashout,CashoutVO.class);
    }
}
