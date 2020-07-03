package com.juchia.tutor.auth.base.authentication.social.qq.connet;


import com.juchia.tutor.auth.base.authentication.social.qq.api.QQ;
import com.juchia.tutor.auth.base.authentication.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 信息适配器
 * 不同的第三放登录，获取到的用户信息不同
 * 框架为我们定义了一个类，将常用的第三方信息封装了，前台从这个类拿通用的信息
 * 我们要把返回来的QQ用户信息适配到这个通用的封装类中
 *
 */
public class QQAdapter implements ApiAdapter<QQ> {

	@Override
	public boolean test(QQ api) {
		return true;
	}

	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		QQUserInfo userInfo = api.getUserInfo();
		
		values.setDisplayName(userInfo.getNickname());
		values.setImageUrl(userInfo.getFigureurl_qq());
		values.setProfileUrl(null);
		values.setProviderUserId(userInfo.getOpenId());
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
        QQUserInfo userInfo = api.getUserInfo();
        UserProfile userProfile= new UserProfile(userInfo.getOpenId(),userInfo.getNickname(),
                null,null,null,null);
		return userProfile;
	}

	@Override
	public void updateStatus(QQ api, String message) {
		//do noting
	}

}
