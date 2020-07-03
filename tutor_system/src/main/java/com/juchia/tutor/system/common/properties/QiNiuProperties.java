package com.juchia.tutor.system.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "juchia.qiniu")
@Data
public class QiNiuProperties {

    private String accessKey; //密钥1
    private String secretKey; //密钥2
    private String bucket; //储存桶名
    private String domain; //七牛个人域名

}
