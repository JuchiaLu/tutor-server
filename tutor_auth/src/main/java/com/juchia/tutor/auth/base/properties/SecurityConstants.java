package com.juchia.tutor.auth.base.properties;

/**
 *通用配置常量
 */
public interface SecurityConstants {
	
	/**
	 * 默认的产生验证码的url前缀
	 */
	String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
	/**
	 * 当请求需要身份认证时，默认跳转的url
	 */
	String DEFAULT_UNAUTHENTICATION_URL = "/user/authentication/require";
	/**
	 * 默认的用户名密码登录的提交url
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/user/authentication/formLoginProcess";
	/**
	 * 默认的手机验证码登录的提交url
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/user/authentication/mobileLoginProcess";
	/**
	 * 默认的OPENID登录的提交url
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/user/authentication/openid";
	/**
	 * 默认登录页面
	 */
	String DEFAULT_SIGN_IN_PAGE_URL = "/signIn.html";
	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数
	 */
	String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
	/**
	 * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数
	 */
	String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
	/**
	 * 发送短信验证码 或 验证短信验证码时，传递手机号的参数
	 */
	String DEFAULT_PARAMETER_NAME_MOBILE = "mobileNumber";
	/**
	 * openid参数名
	 */
	String DEFAULT_PARAMETER_NAME_OPENID = "openId";
	/**
	 * providerId参数名
	 */
	String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";
	/**
	 * session失效默认的跳转地址
	 */
	String DEFAULT_SESSION_INVALID_URL = "/session-invalid.html";
	/**
	 * 获取第三方用户信息的url
	 */
	String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";
}
