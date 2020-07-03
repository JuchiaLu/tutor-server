package com.juchia.tutor.upms.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juchia.tutor.common.entity.ErrorResult;
import com.juchia.tutor.common.enums.ErrorResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //private RequestCache requestCache = new HttpSessionRequestCache();
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();

        Map<String,String> errorsMap = new HashMap<>();
        errorsMap.put("error","请登录后访问");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResult<>(ErrorResultEnum.UNAUTHENTICATED,errorsMap)));
    }
}
