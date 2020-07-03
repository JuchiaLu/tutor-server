/**
 * 
 */
package com.juchia.tutor.auth.base.authentication.social.weixin.config;


import com.juchia.tutor.auth.base.authentication.social.support.ConnectView;
import com.juchia.tutor.auth.base.authentication.social.weixin.connect.WeixinConnectionFactory;
import com.juchia.tutor.auth.base.properties.SecurityProperties;
import com.juchia.tutor.auth.base.properties.WeixinProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 */
@Configuration
@ConditionalOnProperty(prefix = "juchia.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
				weixinConfig.getAppSecret());
	}

    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new ConnectView();
    }

}
