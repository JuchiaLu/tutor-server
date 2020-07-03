package com.juchia.tutor.business.auth.entity.bo;

import com.juchia.tutor.business.common.validate.annotation.Phone;
import com.juchia.tutor.business.common.validate.group.Insert;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 预约表
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Data
public class NeedBO1 implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    //private Long id;

    /**
     * 称呼 详情页展示 如张同学 必选
     */
    @NotEmpty(message = "昵称必填",groups = Insert.class)
    private String nickname;

    /**
     * 联系电话 教员中标后才展示,详情页面不展示 必选
     */
    @NotEmpty(message = "联系电话必填",groups = Insert.class)
    @Phone(message = "联系电话格式错误",groups = Insert.class)
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
    @NotNull(message = "性别必填",groups = Insert.class)
    private Integer gender;

    /**
     * 教师性别要求 1男 2女 3不限 搜索条件 必选
     */
    @NotNull(message = "教师性别要求必填",groups = Insert.class)
    private Integer teacherGender;

    /**
     * 状态 1审核中 2审核不通过 3审核通过 4已选定 5已完成 6已关闭
     */
    //private Integer state;

    /**
     * 家长id_外键(用户)
     */
    //private Long studentId;

    /**
     * 一级分类_外键 搜索条件 如小学 必选
     */
    @NotNull(message = "学员阶段必填",groups = Insert.class)
    private Long firstCategoryId;

    /**
     * 二级分类_外键 搜索条件 如一年级 必选
     */
    @NotNull(message = "学员年级必填",groups = Insert.class)
    private Long secondCategoryId;

    /**
     * 三级分类_外键 搜索条件 如数学 必选
     */
    @NotNull(message = "辅导科目必填",groups = Insert.class)
    private Long thirdCategoryId;

    /**
     * 城市_外键,搜索条件 必选
     */
    @NotNull(message = "所在城市必填",groups = Insert.class)
    private Long cityId;

    /**
     * 地区_外键,搜索条件 必选
     */
    @NotNull(message = "所在地区必填",groups = Insert.class)
    private Long areaId;

    /**
     * 学员类型_外键  字典表 如拔尖型 必选
     */
    @NotNull(message = "学员类型必填",groups = Insert.class)
    private Long studentTypeId;

    /**
     * 教师类型_外键 字典表 如在读大学生 必选
     */
    @NotNull(message = "教师类型必填",groups = Insert.class)
    private Long teacherTypeId;

    /**
     * 老师评论id_外键
     */
    //private Long teacherCommentId;

    /**
     * 家长评论id_外键
     */
    //private Long studentCommentId;

    /**
     * 详细地址 详情页展示 必选
     */
    @NotEmpty(message = "详细地址必填",groups = Insert.class)
    private String address;

    /**
     * 其他要求 可选
     */
    private String demand;

    /**
     * 可上课时间, 序列化后的对象 必选
     */
    @NotEmpty(message = "可上课时间必填",groups = Insert.class)
    private String teachDate;

    /**
     * 预约总人数
     */
    //private Long totalAppoint;

    /**
     *  上课次数 必选
     */
    @NotNull(message = "上课次数必填",groups = Insert.class)
    private Long frequency;

    /**
     *  每次上课小时 必选
     */
    @NotNull(message = "每次上课小时数必填",groups = Insert.class)
    private Long timeHour;

    /**
     *  每小时价格 必选
     */
    @NotNull(message = "每小时价格必填",groups = Insert.class)
    private BigDecimal hourPrice;

    /**
     * 总价格
     */
   // private BigDecimal totalPrice;

    /**
     * 关闭原因
     */
    //private String reason;

    /**
     * 排序
     */
    //private Long weight;

    /**
     * 家长是否已删除(软)
     */
    //private Integer deleted;

    /**
     * 状态 0禁用 1启用
     */
    //private Integer status;

    /**
     * 创建时间
     */
    //private LocalDateTime createTime;

    /**
     * 修改时间
     */
    //private LocalDateTime updateTime;


}
