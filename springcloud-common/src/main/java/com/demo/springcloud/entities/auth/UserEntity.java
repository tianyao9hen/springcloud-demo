package com.demo.springcloud.entities.auth;

import com.demo.springcloud.entities.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserEntity
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends CommonEntity<UserEntity> {

    private static final long serialVersionUID = 4480104195884517060L;

    private String loginName;

    private String loginPassword;

    private String userName;
}