package com.juchia.tutor.auth.base.authentication.social.qq.connet;

import com.juchia.tutor.auth.base.authentication.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 连接工厂
 * 将它需要的信息：
 *                  提供者ID（用来生成触发QQ登录的地址）
 *                  服务提供者（框架拿到access_token后给它，它会用来获取QQ信息）
 *                  适配器（用来将获取到的信QQ息填充到通用第三方信息封装类中）
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
