package com.demo.springcloud.enumType;

/**
 * FwWebError
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-10
 */
public enum FwWebError {

    //auth
    NO_LOGIN(401,"用户未登陆！"),
    EMPTY_LOGINNAME_OR_LOGINPASSWORD(402,"用户名和密码不能为空！"),
    WRONG_LOGNNAME_OR_LOGINPASSWORD(403,"用户名或密码输入有误！"),
    REDIS_WRONG(405,"redis保存错误！"),
    ENCRYPTION_ERRORS(404,"加密错误，请重新登陆！");


    public final int code;
    public final Object msg;

    FwWebError(int code,Object msg){
        this.code = code;
        this.msg = msg;
    }
}