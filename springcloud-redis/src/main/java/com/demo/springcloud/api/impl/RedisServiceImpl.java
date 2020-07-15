package com.demo.springcloud.api.impl;

import com.demo.springcloud.constant.Constant;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.enumType.FwWebError;
import com.demo.springcloud.exception.ServiceReturnException;
import com.demo.springcloud.api.RedisService;
import com.demo.springcloud.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

/**
 * RedisServiceImpl
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-15
 */
@Service
public class RedisServiceImpl implements RedisService{

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String login(UserEntity userEntity,String token) {
        if(userEntity == null || userEntity.getId() == null || "".equals(userEntity.getId()) || token == null || "".equals(token)){
            throw new ServiceReturnException(FwWebError.WRONG_LOGNNAME_OR_LOGINPASSWORD);
        }
        Base64.Encoder encoder = Base64.getEncoder();
        String encodeId = new String(encoder.encode((userEntity.getId()).getBytes()));

        redisUtil.delByPrex(Constant.LOGIN_REDIS_PRE + encodeId + ":*");

        String idKey = encodeId + ":" + new Date().getTime();
        String key = Constant.LOGIN_REDIS_PRE + idKey;

        boolean redisResult = redisUtil.set(key, token, Constant.LOGIN_REDIS_TIMEOUT);
        if (!redisResult) {
            throw new ServiceReturnException(FwWebError.REDIS_WRONG);
        }
        return new String(encoder.encode(idKey.getBytes()));
    }

    @Override
    public String getTokenUser(String idToken) {
        Base64.Decoder decoder = Base64.getDecoder();
        String idkey = new String(decoder.decode(idToken));
        boolean isHas = redisUtil.hasKey(Constant.LOGIN_REDIS_PRE + idkey);
        if(!isHas){
            throw new ServiceReturnException(FwWebError.NO_LOGIN);
        }
        String token = (String) redisUtil.get(Constant.LOGIN_REDIS_PRE + idkey);
        return token;
    }
}