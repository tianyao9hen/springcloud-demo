package com.demo.springcloud.config;

import com.demo.springcloud.interceptor.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

/**
 * IntercepterConfig
 * 拦截器配置，主要设置白名单
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-16
 */
@Configuration
public class IntercepterConfig implements WebMvcConfigurer{

    @Autowired
    private GlobalInterceptor globalInterceptor;

    private Set<String> urlSet;

    @Value("${whitelist.urlset}")
    public void setUrlSet(Set<String> urlSet){
        this.urlSet = urlSet;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有请求
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(globalInterceptor).addPathPatterns("/**");
        //设置白名单
        for (String url : urlSet) {
            interceptorRegistration.excludePathPatterns(url);
        }

    }
}