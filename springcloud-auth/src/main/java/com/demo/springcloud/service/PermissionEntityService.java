package com.demo.springcloud.service;

import com.demo.springcloud.entities.auth.PermissionEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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