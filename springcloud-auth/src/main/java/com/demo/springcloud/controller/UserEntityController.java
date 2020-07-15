package com.demo.springcloud.controller;

import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.exception.ServiceReturnException;
import com.demo.springcloud.api.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
    public ResultContant login(String loginName, String loginPassword) {
        ResultContant<UserEntity> resultContant = new ResultContant<>();
        try{
            UserEntity userEntity = userEntityService.login(loginName, loginPassword);

            resultContant.setResult(userEntity);
        }catch(ServiceReturnException e){
            resultContant.setError(e.getMsg());
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultContant;
    }

    @PostMapping("/checkUser")
    public UserEntity checkUser(@RequestParam("token") String token, @RequestParam("checkUrl") String checkUrl){
        UserEntity userEntity = userEntityService.checkUser(token, checkUrl);
        return userEntity;
    }
}