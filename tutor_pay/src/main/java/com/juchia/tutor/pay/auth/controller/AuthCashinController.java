package com.juchia.tutor.pay.auth.controller;

import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.pay.auth.entity.query.CashinQuery;
import com.juchia.tutor.pay.auth.entity.vo.CashinVO;
import com.juchia.tutor.pay.auth.service.AuthCashinService;
import com.juchia.tutor.pay.common.entity.po.Cashin;
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
@RequestMapping("auth/cashins/me")
public class AuthCashinController {

    @Autowired
    AuthCashinService authCashinService;


    @ApiOperation(value="分页获取我的收入信息")
    @GetMapping("page")
    public MyPage<CashinVO> pageAppoint(PageQuery pageQuery, CashinQuery cashinQuery){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        MyPage<Cashin> myPage = authCashinService.myPage(id,pageQuery,cashinQuery);
        List<Cashin> records = myPage.getRecords();

        List<CashinVO> voS = BeanCopyUtils.copyList(records, CashinVO.class);
        MyPage<CashinVO> myPage1 = BeanCopyUtils.copyBean(myPage, MyPage.class);

        myPage1.setRecords(voS);

        return myPage1;
    }
}
