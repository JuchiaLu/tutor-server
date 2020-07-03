//package com.juchia.tutor.auth.base.authorization.config;
//
//
//import com.juchia.tutor.auth.base.authentication.code.config.ValidateCodeConfig;
//import com.juchia.tutor.auth.base.authentication.email.EmailCodeAuthenticationSecurityConfig;
//import com.juchia.tutor.auth.base.authentication.form.FormAuthenticationSecurityConfig;
//import com.juchia.tutor.auth.base.authentication.handler.MyAccessDeniedHandler;
//import com.juchia.tutor.auth.base.authentication.handler.MyAuthenticationEntryPoint;
//import com.juchia.tutor.auth.base.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.social.security.SpringSocialConfigurer;
//
///**
// * 资源服务器配置
// *
// */
//@Configuration
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled=true)
//public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//
//    @Autowired
//    private TokenStore tokenStore;
//
//    @Autowired
//    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
//
//    @Autowired
//    private EmailCodeAuthenticationSecurityConfig emailCodeAuthenticationSecurityConfig;
//
//    @Autowired
//    private FormAuthenticationSecurityConfig formAuthenticationSecurityConfig;
//
//    @Autowired
//    SpringSocialConfigurer imoocSpringSocialConfigurer;
//
//    @Autowired
//    ValidateCodeConfig validateCodeConfig;
//
////    @Autowired
////    TokenSecurityConfig tokenSecurityConfig;
//
//    @Autowired
//    MyAuthenticationEntryPoint myAuthenticationEntryPoint;
//
//    @Autowired
//    MyAccessDeniedHandler myAccessDeniedHandler;
//
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        //        表单登录
//        formAuthenticationSecurityConfig.configure(http);
//
////        短信验证码登录
//        http.apply(smsCodeAuthenticationSecurityConfig);
//
//        //       邮箱登录
//        http.apply(emailCodeAuthenticationSecurityConfig);
//
////        社交登录登录
//        http.apply(imoocSpringSocialConfigurer);
//
////       验证码
//        http.apply(validateCodeConfig);
//
////        自定义token 自动登录
////        http.apply(tokenSecurityConfig);
//
//        http
//                .exceptionHandling()
////               无权访问处理器
//                .accessDeniedHandler(myAccessDeniedHandler)
////               未登录时的跳转端点
//                .authenticationEntryPoint(myAuthenticationEntryPoint)
////              无状态session
//                //.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                放行登录等端点
//                .and().authorizeRequests().antMatchers(
//    "/auth/**",
//                "/code/**",
//                "/index.html",
//                "/reg.html",
//                "/getpass.html",
//                "/login.html",
//                "/binding.html",
//                "/socialUser",
//                "/users/isExist/**",
//                "/users/**",
//                "oauth/**",
//
//
//                "/auth/**",
//                "/code/**",
//                "/social/unbinding"
//        ).permitAll()
////                其他端点认证后访问
//                .anyRequest().authenticated()
////                开启跨资源和关闭跨站
//                .and().cors().and().csrf().disable();
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(tokenStore);
//    }
//
//}