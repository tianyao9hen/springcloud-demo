package com.demo.springcloud.entities.auth;

import com.demo.springcloud.entities.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.List;

/**
 * RoleEntity
 * 角色实体类
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity extends CommonEntity<RoleEntity> {

    private static final long serialVersionUID = 6712139361624180636L;

    private String roleName;

    private String description;

    private List<PermissionEntity> permissionList;
}