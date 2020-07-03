package com.juchia.tutor.auth.base.properties;

/**
 * 做为OAuth2认证服务器，管理的客户端配置
 *
 */
public class OAuth2ClientProperties {
	
	/**
	 * 第三方应用appId
	 */
	private String clientId;
	/**
	 * 第三方应用appSecret
	 */
	private String clientSecret;
	/**
	 * 针对此应用发出的token的有效时间
	 */
	private int accessTokenValidateSeconds = 7200;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public int getAccessTokenValidateSeconds() {
		return accessTokenValidateSeconds;
	}

	public void setAccessTokenValidateSeconds(int accessTokenValidateSeconds) {
		this.accessTokenValidateSeconds = accessTokenValidateSeconds;
	}
	
}
