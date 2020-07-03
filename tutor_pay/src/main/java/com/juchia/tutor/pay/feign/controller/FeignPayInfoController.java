package com.juchia.tutor.pay.feign.controller;

import com.juchia.tutor.api.pay.bo.PayInfoBO;
import com.juchia.tutor.api.pay.client.IFeignPayInfo;
import com.juchia.tutor.pay.feign.service.FeignPayInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/feign/payInfo") //TODO 服务间调用是否经过网关?
@Slf4j
public class FeignPayInfoController implements IFeignPayInfo {

    @Autowired
    FeignPayInfoService feignPayInfoService;

    @Override
    @PostMapping
    public String savePayInfo(PayInfoBO payInfo) {
        return feignPayInfoService.savePayInfo(payInfo);
    }
}
