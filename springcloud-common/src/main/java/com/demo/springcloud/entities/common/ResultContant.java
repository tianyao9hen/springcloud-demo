package com.demo.springcloud.entities.common;

import com.demo.springcloud.enumType.FwWebError;
import com.demo.springcloud.exception.ServiceReturnException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.map.HashedMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ResultContant
 * 通用的返回值实体类
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultContant implements Serializable {

    private static final long serialVersionUID = 7040730622665766599L;

    private boolean success;

    private Object result;

    private Object error;

    private Integer errorCode;

    public void setError(Object error,Integer errorCode){
        success = false;
        this.error = error;
        this.errorCode = errorCode;
    }

    public void setError(ServiceReturnException e){
        success = false;
        this.error = e.getMsg();
        this.errorCode = e.getCode();
    }

    public void setError(FwWebError fwWebError){
        success = false;
        this.error = fwWebError.msg;
        this.errorCode = fwWebError.code;
    }

    public void setResult(Object result){
        success = true;
        this.result = result;
    }

    public Boolean isSuccess(){
        return success;
    }
}