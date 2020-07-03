package com.juchia.tutor.upms.back.entity.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.juchia.tutor.upms.common.entity.po.User;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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
public class UserQuery1 implements Serializable {

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


    public QueryWrapper<User> buildQueryWrapper(){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("front",0);//不是前台用户

        if(StringUtils.isNotBlank(getUsername())){
            queryWrapper.like("username",getUsername());
        }

        if(StringUtils.isNotBlank(getNickname())){
            queryWrapper.or();
            queryWrapper.like("nickname",getNickname());
        }


        return queryWrapper;
    }

}
