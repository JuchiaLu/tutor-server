package com.juchia.tutor.upms.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("permission")
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private Long parentId;

    private String name;

    private String value;

    private String uri;

    private String icon;

    private Integer type;

    private Long weight;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
