package com.juchia.tutor.auth.base.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 短信登录验证信息封装类
 *
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

//	手机号码
	private final Object principal;

//	这个构造函数给未认证时，封装未认证过的信息
	public SmsCodeAuthenticationToken(String mobile) {
		super(null);
		this.principal = mobile;
		setAuthenticated(false);
	}

//   这个构造函数给认证过后，封装认证过的信息
	public SmsCodeAuthenticationToken(Object principal,
			Collection<? extends GrantedAuthority> authorities) {

//      权限集合
	    super(authorities);

//	    手机号
		this.principal = principal;

//		已认证过后设置为 true
//		本类也有这个方法，但本类是用来给未认证时调用的，防止错误调用，所以这里只能调用的是父类的
		super.setAuthenticated(true);
	}


	public Object getCredentials() {
		return null;
	}

	public Object getPrincipal() {
		return this.principal;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"用户刚提交登录，还没认证过，这个Authentication不能设置为已认证过");
		}

		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
	}
}
