package com.juchia.tutor.auth.base.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 认证失败处理器
 */
@Component
@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

//  Json工具
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException{
	    log.info("登录失败"+exception.getMessage());

        Map<String,String> map = new HashedMap();

        map.put("code","1");
        map.put("message",exception.getMessage());
        map.put("data","");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));
	}

}
