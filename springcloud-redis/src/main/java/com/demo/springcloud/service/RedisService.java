package com.demo.springcloud.service;

import com.demo.springcloud.entities.auth.UserEntity;

/**
 * redisService
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-15
 */
public interface RedisService {

    String login(UserEntity userEntity,String token);

    String getTokenUser(String token);

    Integer refreshUserTime(String token);
}