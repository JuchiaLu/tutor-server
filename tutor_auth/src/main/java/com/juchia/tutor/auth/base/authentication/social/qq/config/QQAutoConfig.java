package com.juchia.tutor.auth.base.authentication.social.qq.config;


import com.juchia.tutor.auth.base.authentication.social.qq.connet.QQConnectionFactory;
import com.juchia.tutor.auth.base.authentication.social.support.ConnectView;
import com.juchia.tutor.auth.base.properties.QQProperties;
import com.juchia.tutor.auth.base.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * QQ登录配置类
 * 生成一个连接工厂，connectionFactoryLocator会管理这个工厂
 */
@Configuration
@ConditionalOnProperty(prefix = "juchia.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQProperties qqConfig = securityProperties.getSocial().getQq();
		return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
	}

    //	绑定或解绑结果视图，为什么将放在这里，请参考 ConnectView 类
    @Bean({"connect/qqConnect", "connect/qqConnected"})
    @ConditionalOnMissingBean(name = "qqConnectedView")
    public View qqConnectedView() {
        return new ConnectView();
    }
}
