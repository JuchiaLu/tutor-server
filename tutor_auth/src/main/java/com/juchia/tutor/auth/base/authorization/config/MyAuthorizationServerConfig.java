/**
 * 
 */
package com.juchia.tutor.auth.base.authorization.config;


import com.juchia.tutor.auth.base.authentication.oauth2.SmsCodeTokenGranter;
import com.juchia.tutor.auth.base.authentication.oauth2.SocialTokenGranter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.social.connect.ConnectionFactoryLocator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService myUserDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private ApprovalStore approvalStore;

    @Autowired
    DataSource dataSource;

    //   也可使用构造函数注入authenticationManager
//    public MyAuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
//    }

    /**
     * 客户端配置 JdbcClientDetailsService JdbcTokenStore AuthorizationServerTokenServices DefaultTokenServices
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);

        System.out.println(passwordEncoder.encode("secret1"));
//        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
//        builder.withClient("client1")
//                .secret(passwordEncoder.encode("secret1"))
//                .redirectUris("http://localhost:9090/login")
//                .autoApprove(true) // 自动授权,不用按钮确认
//                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token","sms_code")
//                .scopes("all")
//        .and().withClient("resource-server")
//                .secret(passwordEncoder.encode("resource-server"))
//                .redirectUris("http://localhost:9090")
//                .autoApprove(false) // 自动授权,不用按钮确认
//                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
//                .scopes("all")
//        .and().withClient("sso1")
//                .secret(passwordEncoder.encode("sso1"))
//                .redirectUris("http://localhost:9091/login","http://sso1.com:9091/login")
//                .autoApprove(false) // 自动授权,不用按钮确认
//                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
//                .scopes("all")
//        .and().withClient("sso2")
//                .secret(passwordEncoder.encode("sso2"))
//                .redirectUris("http://localhost:9092/login","http://sso2.com:9092/login")
//                .autoApprove(false) // 自动授权,不用按钮确认
//                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
//                .scopes("all")
//        .and().withClient("upms")
//                .secret(passwordEncoder.encode("upms"))
//                .redirectUris("http://localhost:80/login","http://sso2.com:80/login")
//                .autoApprove(false) // 自动授权,不用按钮确认
//                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
//                .scopes("all")
//        .and().withClient("auth")
//                .secret(passwordEncoder.encode("auth"))
//                .redirectUris("http://localhost:8060/login","http://sso2.com:8060/login")
//                .autoApprove(false) // 自动授权,不用按钮确认
//                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
//                .scopes("all")
//        ;
    }

//    密码模式必须配置认证管理器
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
//                .tokenStore()
//                .authorizationCodeServices()
//                .pathMapping("/oauth/confirm_access","/custom/confirm_access")
                .approvalStore(approvalStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(myUserDetailsService)

                .tokenStore(tokenStore)
                .tokenGranter(getTokenGranter(endpoints));

        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * tokenKey的访问权限表达式配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.getWriter().write("测试");
                    }
                })
                .checkTokenAccess("permitAll()") // 远程解析令牌, TODO 应该设为信赖的客户端才允许
                .tokenKeyAccess("permitAll()"); //jwt令牌的公钥
    }

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

//    配置Token granter, 添加自定义的granter
    private TokenGranter getTokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<TokenGranter>(Arrays.asList(endpoints.getTokenGranter()));// 获取默认的granter集合
        granters.add(new SmsCodeTokenGranter(authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()));
        granters.add(new SocialTokenGranter(authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(),
                connectionFactoryLocator));
        return new CompositeTokenGranter(granters);
    }


    @Bean
    public ApprovalStore approvalStore() throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }


//    @Bean
//    JdbcClientDetailsService jdbcClientDetailsService(){
//        return ;
//    }

}
