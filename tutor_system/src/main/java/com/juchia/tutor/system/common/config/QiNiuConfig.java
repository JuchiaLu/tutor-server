package com.juchia.tutor.system.common.config;

import com.juchia.tutor.system.common.properties.QiNiuProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(QiNiuProperties.class)
public class QiNiuConfig {
}
