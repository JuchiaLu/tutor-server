package com.juchia.tutor.auth.base.properties;

// 临时用
import org.springframework.social.autoconfigure.SocialProperties;

/**
 * 微信登录配置项
 *
 *
 */
public class WeixinProperties extends SocialProperties {
	
	/**
	 * 第三方id，用来决定发起第三方登录的url后缀，默认是 weixin。
	 */
	private String providerId = "weixin";

	/**
	 * @return the providerId
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId the providerId to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	

}
