package com.demo.springcloud.entities.auth;

import com.demo.springcloud.entities.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PermissionEntity
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionEntity extends CommonEntity<PermissionEntity> {

    private static final long serialVersionUID = 7537876609999735278L;

    private String code;

    private String url;

    private String description;

    private String parent;

    private Integer sort;

}