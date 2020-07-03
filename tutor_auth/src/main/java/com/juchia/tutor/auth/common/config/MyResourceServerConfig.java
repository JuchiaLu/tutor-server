package com.juchia.tutor.auth.common.config;///**
// *
// */
//package com.juchia.tutor.course.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
///**
// * 资源服务器配置
// *
// * @author zhailiang
// *
// */
//@Configuration
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled=true)
//public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//
////    @Autowired
////    private TokenStore tokenStore;
//
//    @Bean
//    public TokenStore jwtTokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//
//
//    //        对称加密方式
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter(){
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("jwtKey"); //对称加密的密钥
//        return converter;
//    }
//
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .antMatcher("/**").authorizeRequests()
//                .antMatchers(
//                        "/users/userInfo**",
//                        "/users/**"
//                ).permitAll()
//                .anyRequest().authenticated();
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(jwtTokenStore());
//    }
//
//}