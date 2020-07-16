package com.demo.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@RestController
public class Controller {

    @Value("${config.info}")
    private String info;

    @Value("${test.info}")
    private String info2;

    @GetMapping("/gateway/get")
    public String get(){
        return info;
    }

    @GetMapping("/gateway/get2")
    public String get2(){
        return info2;
    }
}