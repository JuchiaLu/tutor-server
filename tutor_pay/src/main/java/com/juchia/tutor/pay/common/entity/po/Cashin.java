package com.juchia.tutor.pay.common.entity.po;

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
 * @since 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cashin")
public class Cashin implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * needID_外键
     */
    private Long needId;

    /**
     * appointID_外键预留
     */
    private Long appointId;

    /**
     * 用户ID_外键
     */
    private Long userId;

    /**
     * 收入金额
     */
    private BigDecimal totalAmount;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 排序
     */
    private Long weight;

    /**
     * 创建时间
     */
    private LocalDateTime cteateTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
