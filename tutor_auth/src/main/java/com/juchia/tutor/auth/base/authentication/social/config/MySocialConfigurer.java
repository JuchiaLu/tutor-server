package com.juchia.tutor.auth.base.authentication.social.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 继承默认的社交登录配置
 * 这是 security 相关的配置
 */
public class MySocialConfigurer extends SpringSocialConfigurer {

	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);

//		改变社交登录拦截的url前缀
		filter.setFilterProcessesUrl("/login");

		return (T) filter;
	}
}
