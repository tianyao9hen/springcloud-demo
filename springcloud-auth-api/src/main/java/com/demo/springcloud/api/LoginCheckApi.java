package com.demo.springcloud.api;

import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * LoginCheckApi
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-15
 */
@Component
@FeignClient("SPRINGCLOUD-AUTH")
@RequestMapping("/auth")
public interface LoginCheckApi {

    @PostMapping("/checkUser")
    public UserEntity checkUser(@RequestParam("token") String token, @RequestParam("checkUrl") String checkUrl);
}