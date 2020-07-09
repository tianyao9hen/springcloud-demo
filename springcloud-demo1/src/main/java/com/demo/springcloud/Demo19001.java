package com.demo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Demo19001
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@SpringBootApplication
@EnableEurekaClient
public class Demo19001 {

    public static void main(String[] args){
        SpringApplication.run(Demo19001.class,args);
    }
}