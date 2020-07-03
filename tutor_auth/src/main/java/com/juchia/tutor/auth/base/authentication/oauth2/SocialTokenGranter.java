package com.juchia.tutor.auth.base.authentication.oauth2;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.security.SocialAuthenticationToken;

import java.util.LinkedHashMap;
import java.util.Map;


public class SocialTokenGranter extends AbstractTokenGranter {

    // 仅仅复制了 ResourceOwnerPasswordTokenGranter，只是改变了 GRANT_TYPE 的值，来验证自定义授权模式的可行性
    private static final String GRANT_TYPE = "social";

    private final AuthenticationManager authenticationManager;

    private  ConnectionFactoryLocator connectionFactoryLocator;

    public SocialTokenGranter(
            AuthenticationManager authenticationManager,
            AuthorizationServerTokenServices tokenServices,
            ClientDetailsService clientDetailsService,
            OAuth2RequestFactory requestFactory,
            ConnectionFactoryLocator connectionFactoryLocator) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE,connectionFactoryLocator);
    }

    protected SocialTokenGranter(
            AuthenticationManager authenticationManager,
            AuthorizationServerTokenServices tokenServices,
            ClientDetailsService clientDetailsService,
            OAuth2RequestFactory requestFactory,
            String grantType,
            ConnectionFactoryLocator connectionFactoryLocator) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
        this.connectionFactoryLocator = connectionFactoryLocator;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        // 获取参数
        String code = parameters.get("code");
        String state = parameters.get("state");
        String providerId = parameters.get("providerId");

        OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);

//        TODO redirect_url 可配置
        String returnToUrl = "http://www.merryyou.cn/auth/"+providerId;
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, returnToUrl, null);
        Connection<?> connection = connectionFactory.createConnection(accessGrant);

        Authentication userAuth = new SocialAuthenticationToken(connection, null);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
//            帐号锁定,关闭等
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
//            社交账号未绑定
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate code: " + code);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
