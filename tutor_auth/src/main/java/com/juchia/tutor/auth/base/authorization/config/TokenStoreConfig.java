package com.juchia.tutor.auth.base.authorization.config;

import com.juchia.tutor.auth.base.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class TokenStoreConfig {

    /**
     * 使用redis存储token的配置，只有在imooc.security.oauth2.tokenStore配置为redis时生效
     */
    @Configuration
    @ConditionalOnProperty(prefix = "juchia.security.oauth2", name = "tokenStore", havingValue = "redis")
    public static class RedisConfig {

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        /**
         * @return
         */
        @Bean
        public TokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

    }

    /**
     * 使用jwt时的配置，默认生效
     */
    @Configuration
//    @ConditionalOnProperty(prefix = "juchia.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
    public static class JwtConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

//        对称加密方式
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey("jwtKey"); //这里使用的对称加密, 资源服务器也要用这个私钥
            return converter;
        }

        //        非对称加密方式
//        @Bean
//        public JwtAccessTokenConverter jwtAccessTokenConverter() {
//            final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//
//            //                                                               证书地址, 密钥
//            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
//
//            converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
//
////            converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
//            return converter;
//        }
        @Bean
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer(){
            return new TokenJwtEnhancer();
        }

    }



}
