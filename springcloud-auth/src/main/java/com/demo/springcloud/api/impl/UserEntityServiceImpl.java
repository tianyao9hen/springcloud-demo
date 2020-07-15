package com.demo.springcloud.api.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.springcloud.constant.Constant;
import com.demo.springcloud.dao.UserEntityMapper;
import com.demo.springcloud.entities.auth.PermissionEntity;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.enumType.FwWebError;
import com.demo.springcloud.exception.ServiceReturnException;
import com.demo.springcloud.api.PermissionEntityService;
import com.demo.springcloud.api.RedisService;
import com.demo.springcloud.api.UserEntityService;
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

    @Autowired
    private RedisService redisService;


    @Override
    public UserEntity login(String loginName, String loginPassword) throws Exception {
        if(loginName.equals("") || loginPassword.equals("")){
//            resultContant.setError("用户名和密码不能为空！");
            throw new ServiceReturnException(FwWebError.EMPTY_LOGINNAME_OR_LOGINPASSWORD);
        }

        UserEntity userEntity = userEntityMapper.queryUserByLoginName(loginName);
        if(userEntity == null){
//            resultContant.setError("用户名或密码输入有误！");
            throw new ServiceReturnException(FwWebError.WRONG_LOGNNAME_OR_LOGINPASSWORD);
        }

        if(!userEntity.getLoginPassword().equals(loginPassword)){
//            resultContant.setError("用户名或密码输入有误！");
            throw new ServiceReturnException(FwWebError.WRONG_LOGNNAME_OR_LOGINPASSWORD);
        }

        List<PermissionEntity> permissionList = permissionEntityService.queryPermissionByUserId(userEntity.getId());
        userEntity.setPermissionList(permissionList);
        userEntity.setLoginPassword(null);


        String token = JwtUtils.generateToken(userEntity, RasUtils.getPrivateKey(Constant.PRI_KEY_PATH));
        String idKey = redisService.login(userEntity, token);
        if(idKey == null || "".equals(idKey)){
            throw new ServiceReturnException(FwWebError.REDIS_WRONG);
        }
        userEntity.setToken(idKey);

        return userEntity;
    }

    @Override
    public UserEntity checkUser(String token, String checkUrl) throws Exception {
        if(token == null || "".equals(token) || checkUrl == null || "".equals(checkUrl)){
            throw new ServiceReturnException(FwWebError.NO_LOGIN);
        }
        String tokenUser = redisService.getTokenUser(token);
        UserEntity userEntity = JwtUtils.getObjectFromToken(tokenUser, RasUtils.getPublicKey(Constant.PUB_KEY_PATH), UserEntity.class);
        if(userEntity == null) {
            throw new ServiceReturnException(FwWebError.NO_LOGIN);
        }

        List<PermissionEntity> permissionList = userEntity.getPermissionList();
        for (Object permissionObject : permissionList) {
            //通过jwt转换过来的列表，每一个列表中的对象都被实际的保存成了一个linkedHashMap,所以这里将每个map再次转换成对象，就可以使用了
            //如果直接使用会报：java.util.LinkedHashMap cannot be cast to xxx
            PermissionEntity permissionEntity = JSON.parseObject(JSONObject.toJSONString(permissionObject, true), PermissionEntity.class);
            if(permissionEntity.getUrl().equals(checkUrl)){
                userEntity.setHasPermission(true);
                //刷新redis有效时长
                redisService.refreshUserTime(token);
                return userEntity;
            }
        }
        throw new ServiceReturnException(FwWebError.NO_PERMISSION);
    }
}