package com.demo.springcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.exception.ServiceReturnException;
import com.demo.springcloud.api.UserEntityService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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

    @PostMapping("/get")
    public String get(HttpServletRequest request) throws UnsupportedEncodingException {
        String userEntityJson = request.getHeader("userEntity");
        String decode = URLDecoder.decode(userEntityJson, "utf-8");
        if(userEntityJson == null){
            return "没有userEntity请求头";
        }
        UserEntity userEntity = JSONObject.parseObject(decode, UserEntity.class);

        return info + ":" + userEntity.getUserName();
    }

    @PostMapping("/login")
    public ResultContant login(String loginName, String loginPassword) {
        ResultContant resultContant = new ResultContant();
        try{
            UserEntity userEntity = userEntityService.login(loginName, loginPassword);
            resultContant.setResult(userEntity);
        }catch(ServiceReturnException e){
            resultContant.setError(e);
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultContant;
    }

    @PostMapping("/checkUser")
    public UserEntity checkUser(@RequestParam("token") String token, @RequestParam("checkUrl") String checkUrl){
        UserEntity userEntity = null;
        try{
            userEntity = userEntityService.checkUser(token, checkUrl);
        }catch(ServiceReturnException e){
            return null;
        }catch(Exception e){
            return null;
        }
        return userEntity;
    }
}