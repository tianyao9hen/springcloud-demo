package com.demo.springcloud.dao;

import com.demo.springcloud.entities.auth.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * UserEntityMapper
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@Mapper
public interface UserEntityMapper {

    UserEntity queryUserByLoginName(@Param("loginName") String loginName);
}