package com.juchia.tutor.auth.base.authentication.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 */
@Component
public class FormAuthenticationSecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

	public void configure(HttpSecurity http) throws Exception {
		http

                .formLogin()
//          登录页面
//			.loginPage("/login.html")
//          提交表单登录的地址
			.loginProcessingUrl("/auth/form")
//           用户名参数名
            .usernameParameter("username")
//           密码参数名
            .passwordParameter("password")
//          设置登录成功处理器
            .successHandler(authenticationSuccessHandler)
//          设置登录失败处理器
            .failureHandler(authenticationFailureHandler);
	}
	
}
