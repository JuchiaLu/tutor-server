package com.juchia.tutor.api.upms.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeignUserInfo implements Serializable {

    private static final long serialVersionUID=1L;

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

    List<String> rolesName; //只包含角色名

    List<String> permissionsValue; //只包含权限值

}
