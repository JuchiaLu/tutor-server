package com.juchia.tutor.auth.base.authentication.config;

import com.juchia.tutor.auth.base.authentication.code.config.ValidateCodeConfig;
import com.juchia.tutor.auth.base.authentication.email.EmailCodeAuthenticationSecurityConfig;
import com.juchia.tutor.auth.base.authentication.form.FormAuthenticationSecurityConfig;
import com.juchia.tutor.auth.base.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

//   短信登录
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    //   邮箱登录
    @Autowired
    private EmailCodeAuthenticationSecurityConfig emailCodeAuthenticationSecurityConfig;

//    表单登录
    @Autowired
    private FormAuthenticationSecurityConfig formAuthenticationSecurityConfig;

//    社交登录
    @Autowired
    SpringSocialConfigurer imoocSpringSocialConfigurer;

//    验证码
    @Autowired
    ValidateCodeConfig validateCodeConfig;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

//    密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

//        表单登录
        formAuthenticationSecurityConfig.configure(http);

//        短信验证码登录
        http.apply(smsCodeAuthenticationSecurityConfig);

//       邮箱登录
        http.apply(emailCodeAuthenticationSecurityConfig);

//        社交登录登录
        http.apply(imoocSpringSocialConfigurer);

//       验证码
        http.apply(validateCodeConfig);


        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
//                放行登录等端点
                .and().authorizeRequests().antMatchers(
                        "/logout",
                "/auth/**",
                "/code/**",
                "/index.html",
                "/reg.html",
                "/getpass.html",
                "/login.html",
                "/binding.html",
                "/socialUser",
                "/users/isExist/**",
                "/users/**",
                "oauth/**",
                "/**/api-docs/**"
                ).permitAll()
//                其他端点认证后访问
                .anyRequest().authenticated()
//                开启跨资源和关闭跨站
                .and().cors().and().csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/static/**");
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
