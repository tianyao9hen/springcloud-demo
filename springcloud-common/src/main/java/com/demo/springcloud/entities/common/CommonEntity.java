package com.demo.springcloud.entities.common;

import java.io.Serializable;

/**
 * CommonEntity
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-08
 */
public class CommonEntity<T extends CommonEntity<T>> implements Serializable {

    private static final long serialVersionUID = 2247277114741253167L;

    private String id;
}