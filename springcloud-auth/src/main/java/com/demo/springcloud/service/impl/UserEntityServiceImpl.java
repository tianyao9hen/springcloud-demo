package com.demo.springcloud.service.impl;

import com.demo.springcloud.constant.Constant;
import com.demo.springcloud.dao.UserEntityMapper;
import com.demo.springcloud.entities.auth.PermissionEntity;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.entities.common.ResultContant;
import com.demo.springcloud.service.PermissionEntityService;
import com.demo.springcloud.service.UserEntityService;
import com.demo.springcloud.utils.JwtUtils;
import com.demo.springcloud.utils.RasUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private PermissionEntityService permissionEntityService;


    @Override
    public ResultContant<UserEntity> login(String loginName, String loginPassword){
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

        List<PermissionEntity> permissionList = permissionEntityService.queryPermissionByUserId(userEntity.getId());
        userEntity.setPermissionList(permissionList);
        userEntity.setLoginPassword(null);

        try {
            String token = JwtUtils.generateToken(userEntity, 30, RasUtils.getPrivateKey(Constant.PRI_KEY_PATH));
            userEntity.setToken(token);
            //UserEntity tokenUser = JwtUtils.getObjectFromToken(token, RasUtils.getPublicKey(Constant.PUB_KEY_PATH), UserEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resultContant.setResult(userEntity);
        return resultContant;
    }
}