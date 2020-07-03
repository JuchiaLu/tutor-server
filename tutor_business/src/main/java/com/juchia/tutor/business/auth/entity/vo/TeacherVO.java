package com.juchia.tutor.business.auth.entity.vo;

import com.juchia.tutor.business.front.entity.vo.AreaVO;
import com.tuyang.beanutils.annotation.CopyProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "老师")
public class TeacherVO implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    /**
     * 用户id_外键
     */
    private Long userId;

    /**
     * 成功次数
     */
    private Long totalSuccess;

    /**
     * 评论条数
     */
    private Long totalComment;

    /**
     * 教学经验(年)
     */
    private Long experience;

    /**
     * 年龄
     */
    private Long age;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 擅长科目
     */
    private String goodAt;

    /**
     * 个人简介
     */
    private String resume;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 邮箱号
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 居住地址
     */
    private String address;

    /**
     * 当前工作
     */
    private String currentJob;

    /**
     * 头像地址
     */
    private String headImage;

    /**
     * 性别 1男 2女
     */
    private Integer gender;

    /**
     * 审核状态 //1已提交 2审核不通过 3审核通过
     */
    private Integer state;

    /**
     * 满意度1-100, 自动计算
     */
    private BigDecimal satisfaction;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 课时费用
     */
    private BigDecimal hourPrice;

    /**
     * 所在省份
     */
    @CopyProperty
    @ApiModelProperty("所在省份")
    private AreaVO province;

    /**
     * 所在城市
     */
    @CopyProperty
    @ApiModelProperty("所在城市")
    private AreaVO city;

    /**
     * 授课时间
     */
    private String teachDate;

    /**
     * 是否实名认证 0否 1是
     */
    private Integer realnameAuth;

    /**
     * 是否大学生认证 0否 1是
     */
    private Integer studentAuth;

    /**
     * 是否在职教师认证 0否 1是
     */
    private Integer teacherAuth;

    /**
     * 审核失败原因
     */
    private String reason;

    /**
     * 最近登录
     */
    private LocalDateTime lastLoginTime;

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


}
