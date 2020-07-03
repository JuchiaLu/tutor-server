package com.juchia.tutor.auth.auth.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
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
}
