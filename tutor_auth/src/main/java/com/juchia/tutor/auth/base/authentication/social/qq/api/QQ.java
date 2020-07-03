package com.juchia.tutor.auth.base.authentication.social.qq.api;

/**
 * 获取QQ信息的接口
 * Oauth2协议获取到access_token就走完了，即能用来获取用户资源了
 * 框架把前面的流程走完了，我们要自己实现用access_token获取QQ用户信息
 * 但作为登录一般要用QQ用户的一个openid做为用户标识
 */
public interface QQ {
	
	QQUserInfo getUserInfo();

}
