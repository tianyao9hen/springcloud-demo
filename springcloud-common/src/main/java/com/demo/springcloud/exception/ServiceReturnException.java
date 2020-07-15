package com.demo.springcloud.exception;

import com.demo.springcloud.enumType.FwWebError;

/**
 * ServiceReturnException
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-10
 */
public class ServiceReturnException extends RuntimeException{

    private static final long serialVersionUID = 1972683609985154428L;

    private int code = 200;
    private Object msg = "未知异常！";

    public int getCode(){
        return code;
    }

    public Object getMsg(){
        return msg;
    }

    public ServiceReturnException(FwWebError fwWebError){
        super(fwWebError.msg.toString());
        this.msg = fwWebError.msg;
        this.code = fwWebError.code;
    }


}