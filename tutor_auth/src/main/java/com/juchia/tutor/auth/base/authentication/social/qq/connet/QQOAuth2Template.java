package com.juchia.tutor.auth.base.authentication.social.qq.connet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 框架要帮我们走完Oauth2流程它需要一些基础的信息
 * 如：
 *      客户端id
 *      客户端密钥
 *      QQ登录地址
 *      拿到code后，用code换取access_token的地址
 *
 */
public class QQOAuth2Template extends OAuth2Template {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}
	

//	真正获取access_token在这个方法中
//  构造函数中我们给它的信息，它会自动拼接请求地址和请求参数
//  返回来的不止access_token，还是刷新令牌，过期时间，授权范围，将这些信息封装到AccessGrant类中
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

//	    覆盖自动生成的回调地址
//        parameters.remove("redirect_uri");
//        parameters.set("redirect_uri","http://www.merryyou.cn/auth/qq");
//	    发送http请求
	    String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		
		logger.info("获取accessToke的响应："+responseStr);
		
		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

//		提取需要的信息
		String accessToken = StringUtils.substringAfterLast(items[0], "=");
		Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
		String refreshToken = StringUtils.substringAfterLast(items[2], "=");
		
		return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}
	

//	创建发送http请求类的方法
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

}
