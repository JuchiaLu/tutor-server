package com.juchia.tutor.upms.back.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberInfo {

    private static final long serialVersionUID=1L;

    private Long id;

    private String username;

    private String nickname;

    private String realName;

    //private String password;

    private String email;

    private String phone;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
