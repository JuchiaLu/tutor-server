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
 * 老师预约表
 * </p>
 *
 * @author juchia
 * @since 2020-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("appoint")
public class Appoint implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 需求id_外键
     */
    private Long needId;

    /**
     * 家长id_外键(用户id)
     */
    private Long studentId;

    /**
     * 老师id_外键(用户id)
     */
    private Long teacherId;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * 学生是否已评论 0未评论 1已评论
     */
    private Integer studentCommentState;

    /**
     * 老师是否已评论 0未评论 1已评论
     */
    private Integer teacherCommentState;

    /**
     * 学生是否已软删除 0未删除 1已删除
     */
    private Integer studentDeleteState;

    /**
     * 老师是否已软删除 0未删除 1已删除
     */
    private Integer teacherDeleteState;

    /**
     * 状态 1已预约 2待付款 已中标 3试教中 4进行中 5待结课 6待关闭 7待评论 8已完成 9已维权 10已关闭
     */
    private Integer state;

    /**
     * 关闭原因
     */
    private String reason;

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
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 总共几次
     */
    private Long frequency;

    /**
     * 每次几小时
     */
    private Long timeHour;

    /**
     * 没小时几元
     */
    private BigDecimal hourPrice;

    /**
     * 老师报价
     */
    private BigDecimal totalPrice;

    /**
     * 称呼
     */
    private String nickname;

    /**
     * 联系手机号
     */
    private String phone;

    /**
     * 微信
     */
    private String wechat;

    /**
     * QQ
     */
    private String qq;

    /**
     * 详细地址
     */
    private String address;


    /**
     * 科目
     */
    private String subject;



}
