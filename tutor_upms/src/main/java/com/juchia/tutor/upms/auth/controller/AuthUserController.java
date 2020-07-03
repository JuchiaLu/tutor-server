package com.juchia.tutor.upms.auth.controller;

import com.juchia.tutor.upms.auth.entity.bo.UserBO2;
import com.juchia.tutor.upms.auth.entity.dto.UserDTO1;
import com.juchia.tutor.upms.auth.entity.vo.UserVO;
import com.juchia.tutor.upms.auth.service.AuthUserService;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 * 从 service中接受DTO 将DTO转换成VO返回给前端
 * 有时VO可以省略，直接把DTO返回给前端，前端再自己根据字段转换成要显示的内容，如将 1 转换成男 2 转成女
 * 可以用DTO替代VO的地方，可以省略掉VO，DTO转VO交给前端处理
 * @author juchia
 * @since 2019-08-23
 */
@Validated
@RestController
@RequestMapping("auth/users")
@Slf4j
public class AuthUserController {


    @Autowired
    private AuthUserService authUserService;

    /**
     *  获取登录用户信息, 包括 User role permission
     */
    @GetMapping("userInfo")
    public UserVO getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());
        UserDTO1 userDTO1 = authUserService.getUserInfoById(id);
        return BeanCopyUtils.copyBean(userDTO1,UserVO.class);
    }


    @PatchMapping("password")
    public void changePassword(@RequestBody UserBO2 userBO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());
        authUserService.changePassword(id,userBO);
    }

}

