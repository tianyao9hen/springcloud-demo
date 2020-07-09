package com.demo.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo1Controller
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@RestController
@RequestMapping("/demo1")
public class Demo1Controller {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/get")
    public String getInfo(){
        return configInfo;
    }
}