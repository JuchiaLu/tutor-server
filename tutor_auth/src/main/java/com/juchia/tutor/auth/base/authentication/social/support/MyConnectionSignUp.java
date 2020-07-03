package com.juchia.tutor.auth.base.authentication.social.support;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * 这个类用于社交登录帐号登录时未绑定系统帐号
 * 我们默认给他注册一个帐号,然后返回一个新帐号的唯一标识
 * 系统会把这个唯一标识(如用户名)和社交帐号关联插到数据库
 * 该类和signupUrl只能二选一, 即要么自动注册本站用户,要么返回401和社交用信息
 */
//@Component
public class MyConnectionSignUp implements ConnectionSignUp {

	@Override
	public String execute(Connection<?> connection) {
		//根据社交用户信息默认创建用户并返回用户唯一标识
		return connection.getDisplayName();
	}

}
