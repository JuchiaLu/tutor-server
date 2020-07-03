package com.juchia.tutor.upms.back.entity.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author juchia
 * @since 2019-11-03
 */
@Data
public class PermissionBO1 implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private Long parentId;

    private String name;

    private String value;

    private String uri;

    private String icon;

    private Integer type;

    private Long weight;

    private Integer status;

    //private LocalDateTime createTime;

    //private LocalDateTime updateTime;


}
