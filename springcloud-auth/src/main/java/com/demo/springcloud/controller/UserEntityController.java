package com.demo.springcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.enumType.FwWebError;
import com.demo.springcloud.exception.ServiceReturnException;
import com.demo.springcloud.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
/**
 * UserController
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@RestController
@RequestMapping("/auth")
@Slf4j
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
            log.error("login 异常："+e.getMessage(),e);
        }
        return resultContant;
    }
    
    @PostMapping("/logout")
    public ResultContant logout(HttpServletRequest request){
        ResultContant resultContant = new ResultContant();
        try{
            String token = request.getHeader("token");
            if(token == null || "".equals(token)){
                resultContant.setError(FwWebError.NO_LOGIN);
            }
            Boolean result = userEntityService.logout(token);
            resultContant.setSuccess(true);
        }catch(Exception e){
            log.error("logout 异常："+e.getMessage(),e);
        }
        return resultContant;
    }

    @PostMapping("/checkUser")
    public UserEntity checkUser(@RequestParam("token") String token, @RequestParam("checkUrl") String checkUrl){
        UserEntity userEntity = new UserEntity();
        try{
            userEntity = userEntityService.checkUser(token, checkUrl);
        }catch(ServiceReturnException e){
            if(e.getCode() == 401) return null;
            else if(e.getCode() == 406) {
                userEntity.setHasPermission(false);
            }
            else return null;
        }catch(Exception e){
            log.error("checkUser异常："+e.getMessage(),e);
            return null;
        }
        return userEntity;
    }
}