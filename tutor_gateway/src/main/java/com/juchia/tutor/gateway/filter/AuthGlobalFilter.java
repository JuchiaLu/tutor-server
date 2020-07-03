package com.juchia.tutor.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if(false){ //TODO 跳过不需要认证的白名单
            return chain.filter(exchange);
        }
        //获取token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        ServerHttpResponse resp = exchange.getResponse();
        if(StringUtils.isBlank(token)){
            //没有token, 403 前端引导到登录页面
            log.info("没有token");
        }else{
            log.info("有token");
            //有token, TODO 授权是在网关统一做还是在每个service中做
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
