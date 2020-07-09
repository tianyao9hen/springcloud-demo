package com.demo.springcloud.service.impl;

import com.demo.springcloud.dao.PermissionEntityMapper;
import com.demo.springcloud.entities.auth.PermissionEntity;
import com.demo.springcloud.service.PermissionEntityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * PermissionEntityServiceImpl
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class PermissionEntityServiceImpl implements PermissionEntityService {

    @Autowired
    private PermissionEntityMapper permissionEntityMapper;

    @Override
    public List<PermissionEntity> queryPermissionByUserId(String userId) {
        if("".equals(userId)) return null;
        return permissionEntityMapper.queryPermissionByUserId(userId);
    }
}