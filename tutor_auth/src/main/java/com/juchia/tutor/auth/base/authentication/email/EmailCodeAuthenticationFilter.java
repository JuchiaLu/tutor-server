package com.juchia.tutor.auth.base.authentication.email;

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
public class EmailCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


//  传递邮箱码的参数 TODO 可配置
	private String emailParameter = "email";

//	是否只支持post请求
	private boolean postOnly = true;


	public EmailCodeAuthenticationFilter() {
//	    要拦截的手机登录提交地址 TODO 可配置
		super(new AntPathRequestMatcher("/auth/email", "POST"));
	}


	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("认证方法不支持: " + request.getMethod());
		}

//		从请求中提取邮箱
		String email = obtainMobile(request);

		if (email == null) {
            email = "";
		}

        email = email.trim();

//		一个未经过认证的AuthenticationToken
		EmailCodeAuthenticationToken authRequest = new EmailCodeAuthenticationToken(email);

//      这个方法就是将 request 添加到 EmailCodeAuthenticationToken 中
//      在 AuthenticationProvider 中，认证成功后会重新 new 一个经过认证的 EmailCodeAuthenticationToken
//      提供一个set方法，重新new后，可以从原来未认证的 EmailCodeAuthenticationToken 中拿出 details，set 到重新 new 的
		setDetails(request, authRequest);

//		调用认证管理器，它会遍历各个 AuthenticationProvider，找到能够认证 EmailCodeAuthenticationToken 类型的 Provider
//      再调用 Provider 的 authenticate 方法
		return this.getAuthenticationManager().authenticate(authRequest);
	}


	/**
	 * 从请求中获取邮箱
	 */
	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(emailParameter);
	}


	protected void setDetails(HttpServletRequest request, EmailCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}


	public void setMobileParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "设置传递邮箱的参数，不能为空值");
		this.emailParameter = usernameParameter;
	}


	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getMobileParameter() {
		return emailParameter;
	}

}
