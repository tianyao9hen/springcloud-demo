package com.demo.springcloud.api;

import com.demo.springcloud.entities.auth.PermissionEntity;

import java.util.List;

/**
 * PermissionEntityService
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
public interface PermissionEntityService {

    List<PermissionEntity> queryPermissionByUserId(String userId);

}