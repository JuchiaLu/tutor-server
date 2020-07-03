package com.juchia.tutor.system.common.config;//package com.juchia.tutor.upms.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//@Configuration
//public class TokenStoreConfigBack {
//
//    /**
//     * 使用jwt时的配置
//     */
//    @Configuration
////    @ConditionalOnProperty(prefix = "imooc.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
//    public static class JwtConfig {
//
////        @Autowired
////        private SecurityProperties securityProperties;
//
//        @Bean
//        public TokenStore jwtTokenStore() {
//            return new JwtTokenStore(jwtAccessTokenConverter());
//        }
//
//
////        对称加密方式
//        @Bean
//        public JwtAccessTokenConverter jwtAccessTokenConverter(){
//            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//            converter.setSigningKey("jwtKey"); //对称加密的密钥
//            return converter;
//        }
//
//
//////        非对称加密方式
////        @Bean
////        public JwtAccessTokenConverter jwtAccessTokenConverter() {
////            final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////
////                                     //                                                               证书地址, 密钥
////            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
////
////            converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
////
//////            converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
////            return converter;
////        }
////
//    }
//
//
//
//}
