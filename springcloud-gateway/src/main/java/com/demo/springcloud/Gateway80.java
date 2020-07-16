package com.demo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Gateway80
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@SpringBootApplication
@EnableDiscoveryClient //注册服务
@EnableFeignClients //使用feign
public class Gateway80 {
    public static void main(String[] args){
        SpringApplication.run(Gateway80.class,args);
    }

}