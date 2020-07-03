package com.juchia.tutor.upms.back.entity.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.juchia.tutor.upms.common.entity.po.Role;
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
public class RoleQuery1 implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private Long parentId;

    private String name;

    private String title;

    private String description;

    private Long weight;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public QueryWrapper<Role> buildQueryWrapper(){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNoneBlank(getName())){
            queryWrapper.like("name",getName());
        }

        if(StringUtils.isNoneBlank(getTitle())){
            queryWrapper.or();
            queryWrapper.like("title",getTitle());
        }

        return queryWrapper;
    }

}
