/**
 * 
 */
package com.juchia.tutor.pay.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 总配置项
 */
@ConfigurationProperties(prefix = "juchia.pay")
@Data
public class PayProperties {

    private AliPayProperties aliPay = new AliPayProperties();
	
}

