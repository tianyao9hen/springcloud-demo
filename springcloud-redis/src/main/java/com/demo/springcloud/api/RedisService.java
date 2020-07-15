package com.demo.springcloud.api;

import com.demo.springcloud.entities.auth.UserEntity;

/**
 * redisService
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-15
 */
public interface RedisService {

    public String login(UserEntity userEntity,String token);

}