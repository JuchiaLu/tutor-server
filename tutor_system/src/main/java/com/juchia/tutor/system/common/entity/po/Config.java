package com.juchia.tutor.system.common.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("config")
public class Config implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 排序
     */
    private Long weight;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
