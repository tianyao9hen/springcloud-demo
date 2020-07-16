package com.demo.springcloud.service.impl;

import com.demo.springcloud.constant.Constant;
import com.demo.springcloud.entities.auth.UserEntity;
import com.demo.springcloud.enumType.FwWebError;
import com.demo.springcloud.exception.ServiceReturnException;
import com.demo.springcloud.service.RedisService;
import com.demo.springcloud.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

/**
 * RedisServiceImpl
 * redis服务
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-15
 */
@Service
public class RedisServiceImpl implements RedisService{

    @Autowired
    private RedisUtil redisUtil;

    /**
    *@Description 
    *@Param 登陆操作，将用户信息进行jwt加密并保存进redis中，返回通过Base64加密的用户id
     *          redis key ： JWT_USER_TOKEN：用户id加密：登陆时间戳
    *@Return 
    */
    @Override
    public String login(UserEntity userEntity,String token) {
        if(userEntity == null || userEntity.getId() == null || "".equals(userEntity.getId()) || token == null || "".equals(token)){
            //方法参数有误
            throw new ServiceReturnException(FwWebError.WRONG_LOGNNAME_OR_LOGINPASSWORD);
        }
        
        Base64.Encoder encoder = Base64.getEncoder();
        //对userEntity的id进行加密
        String encodeId = new String(encoder.encode((userEntity.getId()).getBytes()));

        //模糊删除现在可能存在的同一用户的redis数据，保证同一个账户同一时间只能登陆一次
        redisUtil.delByPrex(Constant.LOGIN_REDIS_PRE + encodeId + ":*");

        //添加时间戳和头表示组成key
        String idKey = encodeId + ":" + new Date().getTime();
        String key = Constant.LOGIN_REDIS_PRE + idKey;

        //保存到redis中，并设置过期时间
        boolean redisResult = redisUtil.set(key, token, Constant.LOGIN_REDIS_TIMEOUT);
        if (!redisResult) {
            throw new ServiceReturnException(FwWebError.REDIS_WRONG);
        }
        //返回 由id和时间戳组成的字符串,并进行加密，作为token
        return new String(encoder.encode(idKey.getBytes()));
    }

    /**
    *@Description 
    *@Param 通过token得到userEntity的jwt token
    *@Return 
    */
    @Override
    public String getTokenUser(String idToken) {
        //解密 idToken
        Base64.Decoder decoder = Base64.getDecoder();
        String idkey = new String(decoder.decode(idToken));
        
        //redis 查询是否存在这个key
        boolean isHas = redisUtil.hasKey(Constant.LOGIN_REDIS_PRE + idkey);
        if(!isHas){
            throw new ServiceReturnException(FwWebError.NO_LOGIN);
        }
        //如果redis中存在这个key，则证明用户已登陆，返回jwt token
        String token = (String) redisUtil.get(Constant.LOGIN_REDIS_PRE + idkey);
        return token;
    }

    /**
    *@Description 
    *@Param 更新用户过期时间
    *@Return 
    */
    @Override
    public Integer refreshUserTime(String idToken) {
        Base64.Decoder decoder = Base64.getDecoder();
        String idkey = new String(decoder.decode(idToken));
        //更新过期时间
        boolean expire = redisUtil.expire(Constant.LOGIN_REDIS_PRE + idkey, Constant.LOGIN_REDIS_TIMEOUT);
        return expire?1:0;
    }

    /**
    *@Description
    *@Param 用户退出
    *@Return
    */
    @Override
    public Boolean logout(String idToken) {
        Base64.Decoder decoder = Base64.getDecoder();
        String idkey = new String(decoder.decode(idToken));
        //删除redis中的相关数据
        redisUtil.del(Constant.LOGIN_REDIS_PRE + idkey);
        return true;
    }
}