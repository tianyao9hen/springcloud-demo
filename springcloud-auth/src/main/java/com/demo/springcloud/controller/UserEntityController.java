package com.demo.springcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.exception.ServiceReturnException;
import com.demo.springcloud.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        UserEntity userEntity = (UserEntity)request.getAttribute("userEntity");
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
        UserEntity userEntity = new UserEntity();
        try{
            userEntity = userEntityService.checkUser(token, checkUrl);
        }catch(ServiceReturnException e){
            e.printStackTrace();
            if(e.getCode() == 401) return null;
            else if(e.getCode() == 406) {
                userEntity.setHasPermission(false);
            }
            else return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return userEntity;
    }
}