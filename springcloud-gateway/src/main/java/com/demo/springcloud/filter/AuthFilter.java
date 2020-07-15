package com.demo.springcloud.filter;

import com.alibaba.fastjson.JSONObject;
import com.demo.springcloud.api.LoginCheckApi;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.enumType.FwWebError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * AuthFilter
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-15
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter,Ordered {

    @Autowired
    private LoginCheckApi loginCheckApi;

    private static Set<String> urlSet = new HashSet<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            chain.filter(exchange);
        }
        String end  = "";
        if(path.lastIndexOf("/") >= 0){
            urlSet.add("/login");
            urlSet.add("/logout");
            urlSet.add("/checkUser");
            end  = path.substring(path.lastIndexOf("/"));
            if(urlSet.contains(end)) {
                return chain.filter(exchange);
            }
        }

        HttpHeaders headers = request.getHeaders();
        List<String> tokenList = headers.get("token");
        if(tokenList==null || tokenList.size()<=0){
            DataBuffer dataBuffer = setResponse(FwWebError.NO_LOGIN, response);
            return response.writeWith(Mono.just(dataBuffer));
        }

        String token = tokenList.get(0);
        //这里选择使用userEntity作为返回值，原因是因为，使用通用的ResultContant因为包含了Object类型的属性无法json序列化
        //如果返回userEntity则认为用户已登陆并且存在该权限，如果没有返回，则用户未登陆或没有权限，直接跳转到登陆页面
        UserEntity userEntity = loginCheckApi.checkUser(token, path);

        if(userEntity == null){
            DataBuffer dataBuffer = setResponse(FwWebError.NO_LOGIN, response);
            return response.writeWith(Mono.just(dataBuffer));
        }else if(!userEntity.getHasPermission()){
            DataBuffer dataBuffer = setResponse(FwWebError.NO_PERMISSION, response);
            return response.writeWith(Mono.just(dataBuffer));
        }

        String json = JSONObject.toJSONString(userEntity);
        try {
            json = URLEncoder.encode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String finalJson = json;
        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set("userEntity", finalJson);
        };
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
        exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private DataBuffer setResponse(FwWebError fwWebError,ServerHttpResponse response){
        ResultContant resultContant = new ResultContant();
        resultContant.setError(fwWebError);
        String json = JSONObject.toJSONString(resultContant);
        DataBuffer buffer = response.bufferFactory().wrap(json.getBytes());
        return buffer;
    }
}