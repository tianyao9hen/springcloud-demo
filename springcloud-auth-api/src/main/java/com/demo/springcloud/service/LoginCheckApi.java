package com.demo.springcloud.service;

import com.demo.springcloud.entities.auth.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * LoginCheckApi
 * AUTH服务的feign接口
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-15
 */
@Component
@FeignClient("SPRINGCLOUD-AUTH") //feign调用的服务注册到注册中心的名称
@RequestMapping("/auth")
public interface LoginCheckApi {

    @PostMapping("/checkUser")
    public UserEntity checkUser(@RequestParam("token") String token, @RequestParam("checkUrl") String checkUrl);
}