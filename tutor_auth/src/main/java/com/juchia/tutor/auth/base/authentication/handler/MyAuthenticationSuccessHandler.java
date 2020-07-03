package com.juchia.tutor.auth.base.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 认证成功处理器
 */
@Component
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    private ObjectMapper objectMapper;

    private RequestCache requestCache = new HttpSessionRequestCache();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("登录成功");

        Map<String,String> map = new HashedMap();

        String redirectUrl = null;
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null && savedRequest.getRedirectUrl()!=null && !savedRequest.getRedirectUrl().contains("error")){
            redirectUrl = savedRequest.getRedirectUrl();
        } else {
            redirectUrl = "/";
        }
        map.put("redirectUrl",redirectUrl);

        //返回跳转地址
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));

    }
}
