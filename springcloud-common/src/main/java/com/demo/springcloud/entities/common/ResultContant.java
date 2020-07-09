package com.demo.springcloud.entities.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

    public void setError(String error){
        success = false;
        this.error = error;
    }

    public Object getError(){
        return error;
    }

    public T getResult(){
        return result;
    }

    public void setResult(T result){
        success = true;
        this.result = result;
    }

    public Boolean isSuccess(){
        return success;
    }
}