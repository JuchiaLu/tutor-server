package com.juchia.tutor.api.pay.client;

import com.juchia.tutor.api.pay.bo.CashoutBO;
import com.juchia.tutor.api.pay.vo.CashoutVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("pay/feign/cashouts")
public interface IFeignCashout {

    /**
     * 添加一条支付信息
     * @param
     * @return 支付跳转地址
     */
    @PostMapping
    CashoutVO saveCashout(@RequestBody CashoutBO cashoutBO);

}
