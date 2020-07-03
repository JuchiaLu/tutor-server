package com.juchia.tutor.auth.base.authentication.code.core;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.juchia.tutor.auth.base.authentication.code.support.ValidateCodeException;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


// 负责收集 哪个url需要什么类型的验证码
@Component("validateCodeFilter")
public class Filter extends OncePerRequestFilter implements InitializingBean {


    @Autowired
    private Manager manager;

    @Autowired
    ObjectMapper objectMapper;


//   一对多map
    private MultiValueMap<String, String> uriMap = new LinkedMultiValueMap<>();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 从配置文件中初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        //		TODO 这里可以改良, 自动添加已经配置的验证码类型, 而不是写死只有两种类型

        //用户配置文件配置的需要图形验证码的地址, 添加进map去
        addUriToMap("/auth/form", "imageCode");

        //用户配置文件配置的需要短信验证码的地址, 添加进map去
        addUriToMap("/auth/mobile", "smsCode");

        //用户配置文件配置的需要短信验证码的地址, 添加进map去
        addUriToMap("/auth/email", "emailCode");
    }

    /**
     * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
     */
    protected void addUriToMap(String rawUri, String type) {
        if (StringUtils.isNotBlank(rawUri)) {
            //逗号分割所有url
            String[] uris = StringUtils.splitByWholeSeparatorPreserveAllTokens(rawUri, ",");
            for (String uri : uris) {
                uriMap.add(uri, type);
                logger.info(uri + "配置需要" + type + "类型验证码");
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        Set<String> requireTypes = getRequireTypes(httpServletRequest);
        for (String requireType : requireTypes) {
            logger.info("校验请求(" + httpServletRequest.getRequestURI() + ")中的验证码,验证码类型" + requireType);
            try {
                manager.validate(httpServletRequest,requireType);
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                Map<String,String> map = new HashedMap();
                map.put("code","1");
                map.put("message",exception.getMessage());
                map.put("data","");
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().write(objectMapper.writeValueAsString(map));
                //throw validateCodeException;
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 获取请求的地址需要的全部校验码类型
     */
    private Set<String> getRequireTypes(HttpServletRequest httpServletRequest) {
        Set<String> result = new HashSet<>();
        String requestURI = httpServletRequest.getRequestURI();
        if (StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {  //只拦截post请求
            uriMap.forEach((uri, types)->{
                if (pathMatcher.match(uri, requestURI)) {
                    logger.info(uri + ": 该地址需要验证码类型为:" + types);
                    result.addAll(types);
                }
            });
        }
        return result;
    }
}
