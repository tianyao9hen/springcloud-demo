package com.demo.springcloud.filter;

import com.alibaba.fastjson.JSONObject;
import com.demo.springcloud.service.LoginCheckApi;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.enumType.FwWebError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * AuthFilter
 * 鉴权拦截器
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

    private static Set<String> urlSet;

    @Value("${whitelist.urlset}")
    public void setUrlSet(Set<String> urlSet){
        this.urlSet = urlSet;
    }

    private static Set<String> fileSet;

    @Value("${whitelist.fileset}")
    public void setFileSet(Set<String> fileSet){
        this.fileSet = fileSet;
    }

    /**
    *@Description 拦截器
    *@Param
    *@Return
    */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //如果是option类型的请求直接放行
        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            chain.filter(exchange);
        }
        //如果请求包含在白名单中直接放行
        if(path.lastIndexOf("/") >= 0){
            if(urlSet.contains(path)){
                return chain.filter(exchange);
            }
        }
        //如果请求的是白名单中规定格式的静态文件，直接放行
        if(path.lastIndexOf(".") > 0){
            String end = path.substring(path.lastIndexOf(".") + 1);
            if(fileSet.contains(end)){
                return chain.filter(exchange);
            }
        }

        //获取header中的token
        HttpHeaders headers = request.getHeaders();
        List<String> tokenList = headers.get("token");
        if(tokenList==null || tokenList.size()<=0){
            //没有token认为没有登陆
            DataBuffer dataBuffer = setResponse(FwWebError.NO_LOGIN, response);
            return response.writeWith(Mono.just(dataBuffer));
        }

        //通过token得到userEntity对象
        String token = tokenList.get(0);
        //这里选择使用userEntity作为返回值，原因是因为，使用通用的ResultContant因为包含了Object类型的属性无法json序列化
        //如果返回userEntity则认为用户已登陆并且存在该权限，如果没有返回，则用户未登陆或没有权限，直接跳转到登陆页面
        UserEntity userEntity = loginCheckApi.checkUser(token, path);

        if(userEntity == null){
            //没有登陆
            DataBuffer dataBuffer = setResponse(FwWebError.NO_LOGIN, response);
            return response.writeWith(Mono.just(dataBuffer));
        }else if(!userEntity.getHasPermission()){
            //没有权限
            DataBuffer dataBuffer = setResponse(FwWebError.NO_PERMISSION, response);
            return response.writeWith(Mono.just(dataBuffer));
        }

        //将用户对象转换成json格式保存到请求头中，转发到其他服务
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

    /**
    *@Description 设置拦截器的优先级
    *@Param
    *@Return
    */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
    *@Description 当鉴权失败时设置响应对象，响应鉴权失败
    *@Param
    *@Return
    */
    private DataBuffer setResponse(FwWebError fwWebError,ServerHttpResponse response){
        ResultContant resultContant = new ResultContant();
        resultContant.setError(fwWebError);
        String json = JSONObject.toJSONString(resultContant);
        DataBuffer buffer = response.bufferFactory().wrap(json.getBytes());
        return buffer;
    }
}