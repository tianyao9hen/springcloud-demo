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
 * 配置全局拦截器，拦截外界请求，检查在请求头中是否有userEntity的json信息
 * 如果有则表示该请求为网关转发的，如果没有则为非法请求
 * 如果存在json信息，则将json转换成userEntity并保存到request的参数中，方便获取调用
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-16
 */
@Component
public class GlobalInterceptor implements HandlerInterceptor{

    /**
    *@Description 前置拦截器，将json转换成userEntity并保存到request的参数中，方便获取调用
    *@Param
    *@Return 
    */
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