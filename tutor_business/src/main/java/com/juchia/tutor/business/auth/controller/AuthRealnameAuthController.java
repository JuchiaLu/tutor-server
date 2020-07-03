package com.juchia.tutor.business.auth.controller;


import com.juchia.tutor.business.auth.entity.bo.RealnameAuthBO1;
import com.juchia.tutor.business.auth.entity.vo.RealnameAuthVO;
import com.juchia.tutor.business.auth.service.AuthRealnameAuthService;
import com.juchia.tutor.business.common.entity.po.RealnameAuth;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 实名认证 前端控制器
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@RestController
@RequestMapping("auth/realnameAuths/me")
public class AuthRealnameAuthController {

    @Autowired
    AuthRealnameAuthService authRealnameAuthService;

    @ApiOperation(value="获取我的实名认证信息")
    @GetMapping
    public RealnameAuthVO getRealnameAuthForTeacher(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        RealnameAuth realnameAuth = null;
        try {
            realnameAuth = authRealnameAuthService.getRealnameAuthForTeacher(id);
        } catch (ResourceNotFondException e) {
            e.printStackTrace();
        }
        return  BeanCopyUtils.copyBean(realnameAuth,RealnameAuthVO.class);
    }

    @PutMapping
    @ApiOperation(value="新增或更新我的实名认证信息")
    public RealnameAuthVO saveOrUpdateRealnameAuthForTeacher(@RequestBody RealnameAuthBO1 realnameAuthBO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        RealnameAuth realnameAuth = authRealnameAuthService.saveOrUpdateRealnameAuthForTeacher(id,realnameAuthBO);
        return BeanCopyUtils.copyBean(realnameAuth,RealnameAuthVO.class);
    }

}

