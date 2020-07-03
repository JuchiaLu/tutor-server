//package com.juchia.tutor.auth;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
//import java.util.Enumeration;
//
//@Component
//public class FeignRequestInterceptor implements RequestInterceptor {
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//
//        HttpServletRequest request = attributes.getRequest();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                String name = headerNames.nextElement();
//                String values = request.getHeader(name);
//                requestTemplate.header(name, values);
//            }
//        }
//    }
//}
