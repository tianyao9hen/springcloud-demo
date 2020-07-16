package com.demo.springcloud.config;

import feign.codec.Decoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * FeignConfig
 * 配置openFegin
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-15
 */
@Configuration
public class FeignConfig {

    /**
    *@Description
    *@Param 我们需要通过feign进行服务间的调用过程中能够将数据以json的形式进行传递，就需要配置feignDecoder，指定请求响应的格式。
    *@Return
    */
    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }

    public ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new GateWayMappingJackson2HttpMessageConverter());
        return new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return httpMessageConverters;
            }
        };
    }

    public class GateWayMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        GateWayMappingJackson2HttpMessageConverter(){
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"));
            setSupportedMediaTypes(mediaTypes);
        }
    }
}