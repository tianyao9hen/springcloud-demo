package com.demo.springcloud.controller;

import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@RestController
@RequestMapping("/auth")
public class UserEntityController {

    @Value("${config.info}")
    private String info;

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("/get")
    public String get(){
        return info;
    }

    @PostMapping("/login")
    public ResultContant login(String loginName, String loginPassword){
        return userEntityService.login(loginName, loginPassword);
    }
}