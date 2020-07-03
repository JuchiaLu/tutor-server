package com.juchia.tutor.auth.base.properties;

// 临时用
import org.springframework.social.autoconfigure.SocialProperties;

/**
 * QQ登录配置项
 *
 */
public class QQProperties extends SocialProperties {
	
	/**
	 * 第三方id，用来决定发起第三方登录的url后缀，默认是 qq。
	 */
	private String providerId = "qq";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
}
