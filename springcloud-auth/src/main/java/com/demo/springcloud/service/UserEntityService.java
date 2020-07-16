package com.demo.springcloud.service;

import com.demo.springcloud.entities.auth.UserEntity;

/**
 * UserService
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
public interface UserEntityService {

    UserEntity login(String loginName,String loginPassword) throws Exception;

    UserEntity checkUser(String token, String checkUrl) throws Exception;
}