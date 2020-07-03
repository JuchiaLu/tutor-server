package com.juchia.tutor.auth.base.authentication.social.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交登录配置主类
 * 这是 social 的配置
 */
@Configuration
@EnableSocial
@Order(1)
public class MySocialConfigurerAdapter extends SocialConfigurerAdapter {

//  数据源，用来供JdbcUsersConnectionRepository使用
	@Autowired
	private DataSource dataSource;

//  隐式注册处理器
	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;


//	保存 Connection 的储存器
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {

//      参数分别是，数据源，连接工厂定位器（管理器），插入到数据库的信息加密方式
	    JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
//	    设置数据表的前缀 TODO 修改
//		repository.setTablePrefix("");

//		设置未绑定时，隐式注册处理器
		if(connectionSignUp != null) {
		    repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}

	/**
	 * 社交登录配置类，供浏览器或app模块引入设计登录配置用。
	 */
	@Bean
	public SpringSocialConfigurer imoocSocialSecurityConfig() {
//		社交登录配置
		MySocialConfigurer configurer = new MySocialConfigurer();

//		未绑定时的跳转地址
		configurer.signupUrl("/binding.html");

		return configurer;
	}

//    工具类
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)) {
        };
	}
//
//	@Bean
//    ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository){
//        ConnectController connectController = new ConnectController(connectionFactoryLocator, connectionRepository);
//        return connectController;
//    }
}
