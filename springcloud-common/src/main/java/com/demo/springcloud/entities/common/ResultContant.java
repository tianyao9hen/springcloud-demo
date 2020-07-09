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
public class ResultContant implements Serializable {

    private static final long serialVersionUID = 7040730622665766599L;

    private boolean status;

    private Object result;


}