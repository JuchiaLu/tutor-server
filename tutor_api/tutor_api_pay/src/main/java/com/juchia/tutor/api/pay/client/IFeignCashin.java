package com.juchia.tutor.api.pay.client;

import com.juchia.tutor.api.pay.bo.CashinBO;
import com.juchia.tutor.api.pay.vo.CashinVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("pay/feign/cashins")
public interface IFeignCashin {

    /**
     * 添加一条支付信息
     * @param
     * @return 支付跳转地址
     */
    @PostMapping
    CashinVO saveCashin(@RequestBody CashinBO cashinBO);

}
