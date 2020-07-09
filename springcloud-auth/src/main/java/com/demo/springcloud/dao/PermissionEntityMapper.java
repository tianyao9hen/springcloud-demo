package com.demo.springcloud.dao;

import com.demo.springcloud.entities.auth.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PermissionEntityMapper
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@Mapper
public interface PermissionEntityMapper {

    List<PermissionEntity> queryPermissionByUserId(@Param("userId") String userId);
}