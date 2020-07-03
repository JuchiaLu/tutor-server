package com.juchia.tutor.auth.base.authentication.social.support;

import lombok.Data;

/**
 *
 * 通用社交登录信息
 * 这个类用来在没有session环境时，将 connection 显示给前端
 *  connection 就是社交登录后，从适配器返回来的通用类
 */
@Data
public class SocialUserInfo {
	
	private String providerId;
	
	private String providerUserId;
	
	private String nickname;
	
	private String headImage;
}
