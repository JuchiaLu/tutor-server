package com.juchia.tutor.api.upms.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FeignUserBO1 implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private String username;

    private String nickname;

    private String realName;

    private String password;

    private String email;

    private String phone;

    private Integer status;
}
