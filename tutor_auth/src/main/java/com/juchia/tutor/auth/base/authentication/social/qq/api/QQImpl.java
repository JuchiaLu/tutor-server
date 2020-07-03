package com.juchia.tutor.auth.base.authentication.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 获取QQ信息的实现类
 * Oauth2协议获取到access_token就走完了，即能用来获取用户资源了
 * 框架把前面的流程走完了，我们要自己实现用access_token获取QQ用户信息
 * 但作为登录一般要用QQ用户的一个openid做为用户标识
 * 所以QQ要求用access_token换取openid，再用openid和appid才能获取到用户信息
 * 所以这个实现类中会在构造函数中提前获取openid
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

//  用access_token换取openid的地址
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
//    private static final String URL_GET_OPENID = "http://localhost:8088/oauth2.0/me?access_token=%s";

//	用openid换取QQ信息的地址
	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
//    private static final String URL_GET_USERINFO = "http://localhost:8088/user/get_user_info?oauth_consumer_key=%s&openid=%s";


//	QQ开放平台给我们的第三方应用id
	private String appId;

//	QQ每个用户唯一开放id，可以用access_token换取
	private String openId;

//	反序列化工具
	private ObjectMapper objectMapper = new ObjectMapper();

//	这两个参数由框架调本类用时传进来
	public QQImpl(String accessToken, String appId) {

	    super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		
		this.appId = appId;

//		用access_token拼接获取openid的url
		String url = String.format(URL_GET_OPENID, accessToken);

//		发送http请求
		String result = getRestTemplate().getForObject(url, String.class);
		
		log.info(result);

//		从返回的信息截取openid
		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}

	@Override
	public QQUserInfo getUserInfo() {

//	    用appid和openid拼接获取QQ用户信息的url
		String url = String.format(URL_GET_USERINFO, appId, openId);

//		发送http请求
		String result = getRestTemplate().getForObject(url, String.class);

        log.info(result);
		
		QQUserInfo userInfo = null;
		try {
//		    反序列化
			userInfo = objectMapper.readValue(result, QQUserInfo.class);

//			将构造函数提前获取到的openid添加到QQ信息实体对象中
			userInfo.setOpenId(openId);

			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败", e);
		}
	}

}
