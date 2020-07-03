package com.juchia.tutor.auth.auth.entity.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserBO1 implements Serializable {
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
