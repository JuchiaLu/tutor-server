package com.juchia.tutor.business.front.entity.vo;

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

    @ApiModelProperty("ID")
    private Long id;

    /**
     * 用户id_外键
     */
    @ApiModelProperty("userId")
    private Long userId;

    /**
     * 成功次数
     */
    @ApiModelProperty("成功次数")
    private Long totalSuccess;

    /**
     * 评论条数
     */
    @ApiModelProperty("评论条数")
    private Long totalComment;

    /**
     * 教学经验(年)
     */
    @ApiModelProperty("教学经验")
    private Long experience;

    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Long age;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 真实姓名
     */
//    private String realname;

    /**
     * 擅长科目
     */
    @ApiModelProperty("擅长科目")
    private String goodAt;

    /**
     * 个人简介
     */
    @ApiModelProperty("个人简介")
    private String resume;

    /**
     * QQ号
     */
    @ApiModelProperty("QQ号")
    private String qq;

    /**
     * 微信号
     */
    @ApiModelProperty("微信号")
    private String wechat;

    /**
     * 邮箱号
     */
//    private String email;

    /**
     * 电话
     */
//    private String phone;

    /**
     * 居住地址
     */
//    private String address;

    /**
     * 当前工作
     */
//    private String currentJob;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String headImage;

    /**
     * 性别 1男 2女
     */
    @ApiModelProperty(value = "性别",notes = "1男 2女")
    private Integer gender;

    /**
     * 审核状态 //1已提交 2审核不通过 3审核通过
     */
//    private Integer state;

    /**
     * 满意度1-100, 自动计算
     */
    @ApiModelProperty("满意度1-100")
    private BigDecimal satisfaction;

    /**
     * 课时费用
     */
    @ApiModelProperty("课时费用")
    private BigDecimal hourPrice;

    /**
     * 教师类型_外键 字典表 搜索条件
     */
//    private Long teacherTypeId;

    /**
     * 居住区域_外键
     */
//    private Long areaId;

    /**
     * 授课时间
     */
    @ApiModelProperty(value = "授课时间",notes = "序列化后对象")
    private String teachDate;

    /**
     * 最近登录
     */
    @ApiModelProperty("最近登录")
    private LocalDateTime lastLoginTime;


    /**
     * 审核失败原因
     */
    private String reason;

    /**
     * 排序
     */
    //private Long weight;

    /**
     * 状态 0启用 1禁用
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


    /**
     * 所教科目
     */
    //@ApiModelProperty("所教科目")
    //private List<CategoryVO> teachCategorys;

    /**
     * 所教地区
     */
    //@ApiModelProperty("所教地区")
    //private List<AreaVO> teachAreas;

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
     * 是否实名认证
     */
    @ApiModelProperty(value ="实名认证",notes = "0否 1是")
    private Integer realnameAuth;

    /**
     * 是否大学生认证
     */
    @ApiModelProperty(value = "是否大学生认证",notes = "0否 1是")
    private Integer studentAuth;

    /**
     * 是否在职教师认证
     */
    @ApiModelProperty(value ="是否在职教师认证",notes = "0否 1是")
    private Integer teacherAuth;


}
