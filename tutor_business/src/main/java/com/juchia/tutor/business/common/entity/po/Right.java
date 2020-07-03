package com.juchia.tutor.business.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("right")
public class Right implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 维权方id_外键(用户)
     */
    private Long fromId;

    /**
     * 被维权方id_外键(用户)
     */
    private Long toId;

    /**
     * 维权类型 1老师维权 2学生维权
     */
    private Integer type;

    /**
     * 维权方获得金额
     */
    private BigDecimal fromPrice;

    /**
     * 被维权方获得金额
     */
    private BigDecimal toPrice;

    /**
     * 状态 1处理中 2维权方胜诉 3被维权方胜诉
     */
    private Integer state;

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
