package com.juchia.tutor.pay.auth.controller;

import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.pay.auth.entity.query.CashoutQuery1;
import com.juchia.tutor.pay.auth.entity.vo.CashoutVO;
import com.juchia.tutor.pay.auth.service.AuthCashoutService;
import com.juchia.tutor.pay.common.entity.po.Cashout;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("auth/cashouts/me")
public class AuthCashoutControllser {

    @Autowired
    AuthCashoutService authCashoutService;

    @ApiOperation(value="分页获取我的提现信息")
    @GetMapping("page")
    public MyPage<CashoutVO> pageAppoint(PageQuery pageQuery, CashoutQuery1 cashoutQuery){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        MyPage<Cashout> myPage = authCashoutService.myPage(id,pageQuery,cashoutQuery);
        List<Cashout> records = myPage.getRecords();

        List<CashoutVO> voS = BeanCopyUtils.copyList(records, CashoutVO.class);
        MyPage<CashoutVO> myPage1 = BeanCopyUtils.copyBean(myPage, MyPage.class);

        myPage1.setRecords(voS);

        return myPage1;
    }

}
