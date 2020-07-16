package com.demo.springcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.springcloud.constant.Constant;
import com.demo.springcloud.dao.UserEntityMapper;
import com.demo.springcloud.entities.auth.PermissionEntity;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.enumType.FwWebError;
import com.demo.springcloud.exception.ServiceReturnException;
import com.demo.springcloud.service.PermissionEntityService;
import com.demo.springcloud.service.RedisService;
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

    @Autowired
    private RedisService redisService;

    /**
    *@Description 登陆
    *@Param
    *@Return
    */
    @Override
    public UserEntity login(String loginName, String loginPassword) throws Exception {
        if(loginName.equals("") || loginPassword.equals("")){
//            resultContant.setError("用户名和密码不能为空！");
            throw new ServiceReturnException(FwWebError.EMPTY_LOGINNAME_OR_LOGINPASSWORD);
        }

        //查询用户信息，对用户名和密码进行比较
        UserEntity userEntity = userEntityMapper.queryUserByLoginName(loginName);
        if(userEntity == null){
//            resultContant.setError("用户名或密码输入有误！");
            throw new ServiceReturnException(FwWebError.WRONG_LOGNNAME_OR_LOGINPASSWORD);
        }

        if(!userEntity.getLoginPassword().equals(loginPassword)){
//            resultContant.setError("用户名或密码输入有误！");
            throw new ServiceReturnException(FwWebError.WRONG_LOGNNAME_OR_LOGINPASSWORD);
        }

        //获取用户权限
        List<PermissionEntity> permissionList = permissionEntityService.queryPermissionByUserId(userEntity.getId());
        userEntity.setPermissionList(permissionList);

        //在加密之前将用户密码清空，密码不能显示出来
        userEntity.setLoginPassword(null);

        //通过jwt对用户信息进行加密
        String token = JwtUtils.generateToken(userEntity, RasUtils.getPrivateKey(Constant.PRI_KEY_PATH));
        //将加密后的用户信息存储进redis中
        String idKey = redisService.login(userEntity, token);
        if(idKey == null || "".equals(idKey)){
            throw new ServiceReturnException(FwWebError.REDIS_WRONG);
        }

        //返回用户信息和前端token
        userEntity.setToken(idKey);
        return userEntity;
    }

    /**
    *@Description 鉴权
    *@Param
    *@Return
    */
    @Override
    public UserEntity checkUser(String token, String checkUrl) throws Exception {
        //参数校验
        if(token == null || "".equals(token) || checkUrl == null || "".equals(checkUrl)){
            throw new ServiceReturnException(FwWebError.NO_LOGIN);
        }
        //通过redis查询用户信息token
        String tokenUser = redisService.getTokenUser(token);
        //使用jwt对用户信息解密
        UserEntity userEntity = JwtUtils.getObjectFromToken(tokenUser, RasUtils.getPublicKey(Constant.PUB_KEY_PATH), UserEntity.class);
        if(userEntity == null) {
            throw new ServiceReturnException(FwWebError.NO_LOGIN);
        }

        //获取用户的权限信息
        List<PermissionEntity> permissionList = userEntity.getPermissionList();
        for (Object permissionObject : permissionList) {
            //通过jwt转换过来的列表，每一个列表中的对象都被实际的保存成了一个linkedHashMap,所以这里将每个map再次转换成对象，就可以使用了
            //如果直接使用会报：java.util.LinkedHashMap cannot be cast to xxx
            PermissionEntity permissionEntity = JSON.parseObject(JSONObject.toJSONString(permissionObject, true), PermissionEntity.class);
            //比较用户是否存在权限
            if(permissionEntity.getUrl().equals(checkUrl)){
                userEntity.setHasPermission(true);
                //刷新redis有效时长
                redisService.refreshUserTime(token);
                return userEntity;
            }
        }
        throw new ServiceReturnException(FwWebError.NO_PERMISSION);
    }

    /**
    *@Description 退出
    *@Param
    *@Return
    */
    @Override
    public Boolean logout(String token) {
        return redisService.logout(token);
    }
}