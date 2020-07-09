package com.demo.springcloud.service.impl;

import com.demo.springcloud.dao.UserEntityMapper;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserServiceImpl
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@Service
@Slf4j
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserEntityMapper userEntityMapper;


    @Override
    public ResultContant<UserEntity> login(String loginName, String loginPassword) {
        ResultContant<UserEntity> resultContant = new ResultContant<>();
        if(loginName.equals("") || loginPassword.equals("")){
            resultContant.setError("用户名和密码不能为空！");
            return resultContant;
        }

        UserEntity userEntity = userEntityMapper.queryUserByLoginName(loginName);
        if(userEntity == null){
            resultContant.setError("用户名或密码输入有误！");
            return resultContant;
        }

        if(!userEntity.getLoginPassword().equals(loginPassword)){
            resultContant.setError("用户名或密码输入有误！");
            return resultContant;
        }

        resultContant.setResult(userEntity);
        return resultContant;
    }
}