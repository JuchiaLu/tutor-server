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
@TableName("organisation")
public class Organisation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private Long parentId;

    private String name;

    private String description;

    private Long weight;

    private Integer status;

    private LocalDateTime createTime; //TODO 全部po使用自动填充字段功能来填充创建时间和修改时间

    private LocalDateTime updateTime;


}
