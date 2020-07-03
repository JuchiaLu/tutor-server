package com.juchia.tutor.upms.feign.entity.dto;

import com.juchia.tutor.upms.common.entity.po.Permission;
import com.juchia.tutor.upms.common.entity.po.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO1 {

    private Long id;

    private String username;

    private String nickname;

    private String realName;

    private String password;

    private String email;

    private String phone;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<Permission> permissions;

    private List<Role> roles;

}
