package com.juchia.tutor.auth.auth.controller;

import com.juchia.tutor.auth.auth.entity.dto.UserDTO;
import com.juchia.tutor.auth.auth.entity.vo.UserVO;
import com.juchia.tutor.auth.auth.service.AuthUserService;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me")
public class AuthUserController {


    @Autowired
    AuthUserService authUserService;

    //获取已登录的认证信息
    @GetMapping("authentication")
    public Authentication getCurrentAuthentication(Authentication authentication) {
        System.out.println(authentication);
        return authentication;
    }

    //获取已登录的用户信息
    @GetMapping
    public UserVO getCurrentUser(Authentication authentication) {

        //UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        //UserDTO userDTO = authUserService.getByUsername(userDetails.getUsername());
        UserDTO userDTO = authUserService.getByUsername("admin");
        return BeanCopyUtils.copyBean(userDTO,UserVO.class);
    }


//    检查邮箱是否可用
    @GetMapping("/emailIsExist/{email}")
    public boolean emailIsExist(@PathVariable("email") String email){
        boolean isExist =  authUserService.emailIsExist(email);
        return isExist;
    }

//    检查手机号是否可用
    @GetMapping("/phoneIsExist/{phone}")
    public boolean phoneIsExist(@PathVariable("phone") String phone){
        boolean isExist =  authUserService.phoneIsExist(phone);
        return isExist;
    }

//    绑定邮箱： 参数为 邮箱 和 验证码， 第一次绑定，如果更换绑定要先验证原来的号
    @PatchMapping("bindingEmail")
    public UserVO bindingEmail(String email,String code){
        String username = "admin";
        UserDTO userDTO =  authUserService.bindingEmail(username,email,code);
        return BeanCopyUtils.copyBean(userDTO,UserVO.class);
    }

//    绑定手机号： 参数为 手机号 和 验证码 第一次绑定，如果更换绑定要先验证原来的号
    @PatchMapping("bindingPhone")
    public UserVO bindingPhone(String phone,String code){
        String username = "";
        UserDTO userDTO =  authUserService.bindingPhone(username,phone,code);
        return BeanCopyUtils.copyBean(userDTO,UserVO.class);
    }


//    重设密码： 参数为 邮箱验证码 和 新密码
    @PatchMapping("emailCodeUpdatePassword")
    public UserVO emailCodeUpdatePassword(String newPassword,String code){
        String username = "";
        UserDTO userDTO =  authUserService.emailCodeUpdatePassword(username,newPassword,code);
        return BeanCopyUtils.copyBean(userDTO,UserVO.class);
    }

//    重设密码： 参数为 短信验证码 和 新密码
    @PatchMapping("phoneCodeUpdatePassword")
    public UserVO phoneCodeUpdatePassword(String newPassword,String code){
        String username = "";
        UserDTO userDTO =  authUserService.phoneCodeUpdatePassword(username,newPassword,code);
        return BeanCopyUtils.copyBean(userDTO,UserVO.class);
    }


//    重设密码发送邮箱验证码 或 解绑邮箱
    @PatchMapping("sendEmailCodeForUser")
    public UserVO sendEmailCodeUpdatePassword(String username){
        UserDTO userDTO =  authUserService.sendEmailCodeForUser(username);
        return BeanCopyUtils.copyBean(userDTO,UserVO.class);
    }

//    重设密码发送短信验证码 或 解绑手机
    @PatchMapping("sendPhoneCodeForUser")
    public UserVO sendPhoneCodeUpdatePassword(String username){
        UserDTO userDTO =  authUserService.sendPhoneCodeForUser(username);
        return BeanCopyUtils.copyBean(userDTO,UserVO.class);
    }


}
