package com.juchia.tutor.business.back.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "老师")
public class TeacherQuery1 implements Serializable {
    //private Long id;

    /**
     * 成功次数
     */
    //private Long totalSuccess;

    /**
     * 评论条数
     */
    //private Long totalComment;

    /**
     * 教学经验(年)
     */
    //private Long experience;

    /**
     * 年龄
     */
    //private Long age;

    /**
     * 昵称
     */
    //private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 擅长科目
     */
    //private String goodAt;

    /**
     * 个人简介
     */
    //private String resume;

    /**
     * QQ号
     */
    //private String qq;

    /**
     * 微信号
     */
    //private String wechat;

    /**
     * 邮箱号
     */
    //private String email;

    /**
     * 电话
     */
    //private String phone;

    /**
     * 居住地址
     */
    //private String address;

    /**
     * 当前工作
     */
    //private String currentJob;

    /**
     * 头像地址
     */
    //private String headImage;

    /**
     * 性别 1男 2女
     */
    //private Integer gender;

    /**
     * 审核状态 //1已提交 2审核不通过 3审核通过
     */
    private Integer state;

    /**
     * 满意度1-100, 自动计算
     */
    //private BigDecimal satisfaction;

    /**
     * 课时费用
     */
    //private BigDecimal hourPrice;


    /**
     * 居住区域_外键
     */
    //private Long areaId;

    /**
     * 授课时间
     */
    //private String teachDate;

    /**
     * 最近登录
     */
    //private LocalDateTime lastLoginTime;

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


//  以下字段为中间表外键

    /**
     * 所教学科
     */
    @ApiModelProperty(value = "所教学科ID")
    private Long teachCategoryId;

    /**
     * 所教地区
     */
    @ApiModelProperty(value = "所教地区ID")
    private Long teachAreaId;

    @ApiModelProperty(value = "排序字段",notes = "可选 成功次数:totalSuccess,满意度:satisfaction, 课时费用:hourPrice")
    private String  sort;

    @ApiModelProperty(value = "升序或降序",notes = "可选 升序:asc,降序:desc")
    private String order;



//    public QueryWrapper<Teacher> buildQueryWrapper(){
//        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
//        if(getGender()!=null){
//            queryWrapper.eq("gender",getGender());
//        }
//        if(getTeacherTypeId()!=null){
//            queryWrapper.eq("teacher_type_id",getTeacherTypeId());
//        }
//        return queryWrapper;
//    }

}
