package com.juchia.tutor.system.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("route")
public class Route implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private Long parentId;

    private String name;

    private String title;

    private String path;

    private String componentPath;

    private Long weight;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
