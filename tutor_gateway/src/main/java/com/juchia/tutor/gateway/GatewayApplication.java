package com.juchia.tutor.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }

//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/get")
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri("http://www.baidu.com"))
//                .build();
//    }
}
