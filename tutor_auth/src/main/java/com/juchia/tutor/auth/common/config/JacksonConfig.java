package com.juchia.tutor.auth.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化，属性为NULL 不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

//       解决 Long 类型 精度损失
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(module);

        return objectMapper;
    }
//Include.Include.ALWAYS 默认
//Include.NON_DEFAULT    属性为默认值不序列化
//Include.NON_EMPTY       属性为 空（""） 或者为 NULL 都不序列化
//Include.NON_NULL       属性为NULL 不序列化
}
