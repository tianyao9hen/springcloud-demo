package com.demo.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redisConfig
 * redis配置类
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
@Configuration
public class redisConfig {

    /**
    *@Description
    *@Param 设置redisTemplate
    *@Return
    */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //使用fastjson序列化
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(jdkSerializationRedisSerializer);
        template.setHashValueSerializer(jdkSerializationRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(factory);
        return template;
    }
}