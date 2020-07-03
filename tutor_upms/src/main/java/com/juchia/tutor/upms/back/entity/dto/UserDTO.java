package com.juchia.tutor.upms.back.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author juchia
 * @since 2019-11-03
 */
@Data
public class UserDTO implements Serializable {

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
