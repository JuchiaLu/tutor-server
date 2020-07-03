package com.juchia.tutor.auth.base.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 短信登录配置
 *
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

//  认证成功处理器
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

//	认证失败处理器
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

//	从数据库中查出用户信息的服务
	@Autowired
	private UserDetailsService myUserDetailsService;

//	前后端分离不需要记住我功能
////	记住我功能的存储器，如存到数据库的 JdbcTokenRepositoryImpl
//	@Autowired
//	private PersistentTokenRepository persistentTokenRepository;
//
//    @Autowired
//    DataSource dataSource;
//
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
////		tokenRepository.setCreateTableOnStartup(true);
//        return tokenRepository;
//    }
	

	@Override
	public void configure(HttpSecurity http) throws Exception {

//	    短信登录过滤器
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();

//		为短信登录过滤器设置认证管理器
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

//		为短信登录过滤器设置认证成功处理器
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);

//		为短信登录过滤器设置认证失败处理器
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

//		为短信登录过滤器设置记住我服务
//		String key = UUID.randomUUID().toString();
//		smsCodeAuthenticationFilter.setRememberMeServices(new PersistentTokenBasedRememberMeServices(key, userDetailsService, persistentTokenRepository));

//      短信登录提供者（短信登录验证逻辑）
		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();

//		为短信登录提供者设置从数据库中查出用户信息的服务
		smsCodeAuthenticationProvider.setUserDetailsService(myUserDetailsService);

//		以下是真正起作用的配置

//      添加短信认证提供者到提供者集合中
		http.authenticationProvider(smsCodeAuthenticationProvider)
//           将短信登录过滤器添加到用户名密码登录过滤器前
			.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
