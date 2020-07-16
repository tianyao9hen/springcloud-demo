package com.demo.springcloud.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.enumType.FwWebError;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;

/**
 * GlobalInterceptor
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-16
 */
@Component
public class GlobalInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userEntityJson = request.getHeader("userEntity");
        if(userEntityJson == null){
            return false;
        }
        String decode = URLDecoder.decode(userEntityJson,"utf-8");
        UserEntity userEntity = JSONObject.parseObject(decode, UserEntity.class);
        request.setAttribute("userEntity",userEntity);
        return true;
    }
}