package com.juchia.tutor.pay.auth.entity.vo;

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
 * @since 2020-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("pay_info")
public class PayInfoVO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 预约ID_外键
     */
    private Long appointId;

    /**
     * 用户ID_外键
     */
    private Long userId;

    /**
     * 付款给的老师ID_外键
     */
    private Long toId;

    /**
     * 类型 1支付宝 2微信
     */
    private Integer type;

    /**
     * 支付状态 1未支付 2已支付 3已退款 4已关闭
     */
    private Integer state;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * 支付平台流水号
     */
    private String platformTradeNo;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 订单绝对超时时间
     */
    private LocalDateTime timeExpire;

    /**
     * 订单相对超时时间表达式
     */
    private String timeoutExpress;

    /**
     * 退款请求号
     */
    private String requestNo;

    /**
     * 退款时间(支付后退款)
     */
    private LocalDateTime refundTime;

    /**
     * 订单关闭时间(创建后不支付或超时)
     */
    private LocalDateTime closeTime;

    /**
     * 订单成功时间(付款成功后)
     */
    private LocalDateTime successTime;

    /**
     * 订单完成时间(超过退款时间后)
     */
    private LocalDateTime finishTime;

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
