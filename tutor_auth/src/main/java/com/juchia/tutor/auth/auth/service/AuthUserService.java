package com.juchia.tutor.auth.auth.service;

import com.juchia.tutor.api.upms.bo.FeignUserBO1;
import com.juchia.tutor.api.upms.client.IFeignUser;
import com.juchia.tutor.api.upms.vo.FeignUserInfo;
import com.juchia.tutor.auth.auth.entity.dto.UserDTO;
import com.juchia.tutor.auth.base.authentication.code.core.Manager;
import com.juchia.tutor.auth.common.exception.BusinessException;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthUserService {

    @Autowired
    IFeignUser restUserService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private Manager manager;


    //根据用户名获取用户
    public UserDTO getByUsername(Object principal) {
        String username = (String)principal;
        FeignUserInfo userInfo = restUserService.getUserInfo(username);
        return BeanCopyUtils.copyBean(userInfo,UserDTO.class);
    }

    public boolean emailIsExist(String email) {
        FeignUserInfo has = restUserService.getUserInfo(email);
        if(has==null){
            return false;
        }
        return true;
    }

    public boolean phoneIsExist(String phone) {
        FeignUserInfo has = restUserService.getUserInfo(phone);
        if(has==null){
            return false;
        }
        return true;
    }

    public UserDTO bindingEmail(String username, String email, String code) {

//       校验验证码
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        manager.validate(request,"emailCode");

//        查出用户并添加邮箱
        FeignUserInfo userInfo = restUserService.getUserInfo(username);

        if(StringUtils.isNotBlank(userInfo.getEmail())){
            throw new BusinessException("请先解绑原邮箱后再绑定");
        }

        FeignUserBO1 feignUserBO = BeanCopyUtils.copyBean(userInfo, FeignUserBO1.class);
        feignUserBO.setEmail(email);
        restUserService.saveUser(feignUserBO);

        return BeanCopyUtils.copyBean(feignUserBO,UserDTO.class);
    }


    public UserDTO bindingPhone(String username, String phone, String code) {
        //       校验验证码
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        manager.validate(request,"emailCode");

//        查出用户并添加手机号
        FeignUserInfo userInfo = restUserService.getUserInfo(username);
        FeignUserBO1 feignUserBO = BeanCopyUtils.copyBean(userInfo, FeignUserBO1.class);
        feignUserBO.setEmail(phone);
        restUserService.saveUser(feignUserBO);

        return BeanCopyUtils.copyBean(feignUserBO,UserDTO.class);
    }

    public UserDTO emailCodeUpdatePassword(String username, String newPassword, String code) {
        return null;
    }

    public UserDTO phoneCodeUpdatePassword(String username, String newPassword, String code) {
        return null;
    }

    public UserDTO sendEmailCodeForUser(String username) {
        return null;
    }

    public UserDTO sendPhoneCodeForUser(String username) {
        return null;
    }
}
