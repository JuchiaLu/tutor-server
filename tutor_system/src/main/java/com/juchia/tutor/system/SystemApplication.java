package com.juchia.tutor.system;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.juchia.tutor.system.common.mapper")
//@EnableDiscoveryClient
@EnableSwagger2Doc
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }


    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        //sql格式化
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    @Bean
    public PaginationInterceptor PaginationInterceptorpaginationInterceptor() {
        return new PaginationInterceptor();
    }

}
