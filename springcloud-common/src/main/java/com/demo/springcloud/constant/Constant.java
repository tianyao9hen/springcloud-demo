package com.demo.springcloud.constant;

/**
 * 公共字段
 * Constant
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-08
 */
public class Constant {

    /**
    *@Description
    *@Param 公钥所在地址
    *@Return
    */
    public static final String PUB_KEY_PATH = "static\\ras.pub";

    /**
    *@Description
    *@Param 私钥所在地址
    *@Return
    */
    public static final String PRI_KEY_PATH = "static\\ras.pri";

    /**
    *@Description
    *@Param 保存在redis中的用户信息头
    *@Return
    */
    public static final String LOGIN_REDIS_PRE = "JWT_USER_TOKEN:";

    /**
    *@Description
    *@Param 保存在redis中的用户信息有效时长，单位秒
    *@Return
    */
    public static final Long LOGIN_REDIS_TIMEOUT = 7200L;

    /**
     *@Description
     *@Param 保存在redis中的用户信息有效时长，单位分
     *@Return
     */
    public static final Long LOGIN_REDIS_TIMEOUT_MINUTE = LOGIN_REDIS_TIMEOUT / 60;//120L;

}