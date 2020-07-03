package com.juchia.tutor.business.auth.entity.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 老师预约表
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Data
public class AppointBO1 implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
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
     * 订单号
     */
    private String tradeNo;

    /**
     * 老师id_外键(用户id)
     */
    private Long teacherId;

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
     * 老师报价
     */
    private BigDecimal totalPrice;

    /**
     * 状态 1已预约 2待付款 已中标 3试教中 4进行中 5待结课 6待关闭 7待评论 8已完成 9已维权 10已关闭
     */
    private Integer state;

    /**
     * 关闭原因
     */
    private String reson;

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



//    ------------------------------------------------

    /**
     * 称呼 详情页展示 如张同学 必选
     */
    private String nickname;

    /**
     * 联系电话 教员中标后才展示,详情页面不展示 必选
     */
    private String phone;

    /**
     * 微信号 教员中标后才展示,详情页面不展示 可选
     */
    private String wechat;

    /**
     * QQ 教员中标后才展示,详情页面不展示 可选
     */
    private String qq;

    /**
     * 性别 1男 2女 详情页展示 必选
     */
    private Integer gender;

    /**
     * 教师性别要求 1男 2女 3不限 搜索条件 必选
     */
    private Integer teacherGender;


    /**
     * 一级分类_外键 搜索条件 如小学 必选
     */
    private Long firstCategoryId;

    /**
     * 二级分类_外键 搜索条件 如一年级 必选
     */
    private Long secondCategoryId;

    /**
     * 三级分类_外键 搜索条件 如数学 必选
     */
    private Long thirdCategoryId;

    /**
     * 地区_外键,搜索条件 必选
     */
    private Long areaId;

    /**
     * 学员类型_外键  字典表 如拔尖型 必选
     */
    private Long studentTypeId;

    /**
     * 教师类型_外键 字典表 如在读大学生 必选
     */
    private Long teacherTypeId;

    /**
     * 老师评论id_外键
     */
    private Long teacherCommentId;

    /**
     * 家长评论id_外键
     */
    private Long studentCommentId;

    /**
     * 详细地址 详情页展示 必选
     */
    private String address;

    /**
     * 其他要求 可选
     */
    private String demand;

    /**
     * 可上课时间, 序列化后的对象 必选
     */
    private String teachDate;

    /**
     * 预约总人数
     */
    private Long totalAppoint;

    /**
     *  上课次数 必选
     */
    private Long frequency;

    /**
     *  每次上课小时 必选
     */
    private Long timeHour;

    /**
     *  每小时价格 必选
     */
    private BigDecimal hourPrice;


    /**
     * 关闭原因
     */
    private String reason;


    /**
     * 家长是否已删除(软)
     */
    private Integer deleted;


    /**
     * 科目
     */
    private String subject;



}
