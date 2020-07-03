package com.juchia.tutor.auth.base.authentication.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信登录过滤器
 *
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


//  传递手机号码的参数 TODO 可配置
	private String mobileParameter = "mobileNumber";

//	是否只支持post请求
	private boolean postOnly = true;


	public SmsCodeAuthenticationFilter() {
//	    要拦截的手机登录提交地址 TODO 可配置
		super(new AntPathRequestMatcher("/auth/mobile", "POST"));
	}


	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("认证方法不支持: " + request.getMethod());
		}

//		从请求中提取手机号
		String mobile = obtainMobile(request);

		if (mobile == null) {
			mobile = "";
		}

		mobile = mobile.trim();

//		一个未经过认证的AuthenticationToken
		SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

//      这个方法就是将 request 添加到 SmsCodeAuthenticationToken 中
//      在 AuthenticationProvider 中，认证成功后会重新 new 一个经过认证的 SmsCodeAuthenticationToken
//      提供一个set方法，重新new后，可以从原来未认证的 SmsCodeAuthenticationToken 中拿出 details，set 到重新 new 的
		setDetails(request, authRequest);

//		调用认证管理器，它会遍历各个 AuthenticationProvider，找到能够认证 SmsCodeAuthenticationToken 类型的 Provider
//      再调用 Provider 的 authenticate 方法
		return this.getAuthenticationManager().authenticate(authRequest);
	}


	/**
	 * 从请求中获取手机号
	 */
	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(mobileParameter);
	}


	protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}


	public void setMobileParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "设置传递手机号的参数，不能为空值");
		this.mobileParameter = usernameParameter;
	}


	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getMobileParameter() {
		return mobileParameter;
	}

}
