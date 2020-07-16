package com.demo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka7001
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@SpringBootApplication
@EnableEurekaServer //启动eureka注册中心服务
public class Eureka7001 {

    public static void main(String[] args){
        SpringApplication.run(Eureka7001.class,args);
    }
}