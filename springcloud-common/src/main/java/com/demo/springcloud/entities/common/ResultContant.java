package com.demo.springcloud.entities.common;

import com.demo.springcloud.enumType.FwWebError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.map.HashedMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ResultContant
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultContant<T> implements Serializable {

    private static final long serialVersionUID = 7040730622665766599L;

    private boolean success;

    private T result;

    private Object error;

    private Integer errorCode;

    public void setError(Object error){
        success = false;
        this.error = error;
    }

    public void setError(FwWebError fwWebError){
        success = false;
        this.error = fwWebError.msg;
        this.errorCode = fwWebError.code;
    }

    public void setResult(T result){
        success = true;
        this.result = result;
    }

    public Boolean isSuccess(){
        return success;
    }
}