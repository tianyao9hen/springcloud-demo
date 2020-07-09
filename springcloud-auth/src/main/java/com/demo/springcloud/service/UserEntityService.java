package com.demo.springcloud.service;

import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;

/**
 * UserService
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
public interface UserEntityService {

    ResultContant login(String loginName,String loginPassword) throws Exception;
}