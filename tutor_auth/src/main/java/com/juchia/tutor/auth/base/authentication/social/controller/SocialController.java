package com.juchia.tutor.auth.base.authentication.social.controller;

import com.juchia.tutor.auth.base.authentication.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;


@RestController
public class SocialController {


    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 社交登录用户但未与系统用户绑定的用户跳转到这里
     * 并返回社交登录的用户信息给前台
     */
    @GetMapping("/socialUser")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
//	    connection 就是社交登录后，从适配器返回来的通用类
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        if(connection==null){
            return new SocialUserInfo();
        }
        return buildSocialUserInfo(connection);
    }


    @PostMapping("/social/binding")
    public void binding(HttpServletRequest request, Authentication authentication) {
//	    connection 就是社交登录后，从适配器返回来的通用类
        User user = (User) authentication.getPrincipal();
        providerSignInUtils.doPostSignUp(user.getUsername(),new ServletWebRequest(request));
    }






	/**
	 * 根据Connection信息构建SocialUserInfo
     * connection 就是社交登录后，从适配器返回来的通用类
	 */
	protected SocialUserInfo buildSocialUserInfo(Connection<?> connection) {
		SocialUserInfo userInfo = new SocialUserInfo();
		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickname(connection.getDisplayName());
		userInfo.setHeadImage(connection.getImageUrl());
		return userInfo;
	}
	
}
