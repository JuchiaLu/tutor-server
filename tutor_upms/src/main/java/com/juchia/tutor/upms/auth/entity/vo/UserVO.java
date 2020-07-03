package com.juchia.tutor.upms.auth.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tuyang.beanutils.annotation.CopyCollection;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author juchia
 * @since 2019-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class UserVO implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private String username;

    private String nickname;

    private String realName;

//    private String password;

    private String email;

    private String phone;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @CopyCollection(targetClass=PermissionVO.class)
    private List<PermissionVO> permissions;

    @CopyCollection(targetClass=RoleVO.class)
    private List<RoleVO> roles;


}
