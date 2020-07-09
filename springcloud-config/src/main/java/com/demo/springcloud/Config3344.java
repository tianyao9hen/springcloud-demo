package com.demo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Config3344
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@SpringBootApplication
@EnableConfigServer
public class Config3344 {

    public static void main(String[] args){
        SpringApplication.run(Config3344.class,args);
    }
}