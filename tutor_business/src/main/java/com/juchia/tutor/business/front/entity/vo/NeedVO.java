package com.juchia.tutor.business.front.entity.vo;

import com.tuyang.beanutils.annotation.CopyProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 预约表
 * </p>
 *
 * @author juchia
 * @since 2019-12-19
 */
@Data
@ApiModel(description = "首页最新5个需求,")
public class NeedVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "称呼")
    private String nickname;

    @ApiModelProperty(value = "性别",notes = "1男 2女")
    private Integer gender;

    @ApiModelProperty(value = "教师性别要求",notes = "1男 2女 0不限")
    private Integer teacherGender;

    @ApiModelProperty(value = "状态",notes = "1审核中 2审核失败 3审核通过 4已选定 5已完成 6已关闭")
    private Integer state;

    @CopyProperty
    @ApiModelProperty(value = "一级学科",notes = "如小学")
    private CategoryVO firstCategory;

    @CopyProperty
    @ApiModelProperty(value = "二级学科",notes = "如一年级")
    private CategoryVO secondCategory;

    @CopyProperty
    @ApiModelProperty(value = "三级学科",notes = "如数学")
    private CategoryVO thirdCategory;

    @CopyProperty
    @ApiModelProperty(value = "所在城市")
    private AreaVO city;

    @CopyProperty
    @ApiModelProperty(value = "所在地区")
    private AreaVO area;

    @CopyProperty
    @ApiModelProperty(value = "学员类型",notes = "字典表 如拔尖型")
    private DictVO studentType;

    @CopyProperty
    @ApiModelProperty(value = "教师类型",notes = "字典表 如在读大学生")
    private DictVO teacherType;

    @CopyProperty
    @ApiModelProperty(value = "教师评价",notes = "已完成状态才会展示")
    private CommentVO teacherComment;

    @CopyProperty
    @ApiModelProperty(value = "学生评价",notes = "已完成状态才会展示")
    private CommentVO studentComment;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "其他要求")
    private String demand;

    @ApiModelProperty(value = "可上课时间",notes = "序列化后的对象")
    private String teachDate;

    @ApiModelProperty(value = "预约总人数")
    private Long totalAppoint;

    @ApiModelProperty(value = "上课次数")
    private Long frequency;

    @ApiModelProperty(value = "每次上课小时")
    private Long timeHour;

    @ApiModelProperty(value = "每小时价格")
    private BigDecimal hourPrice;

    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "排序")
    private Long weight;

    @ApiModelProperty(value = "状态",notes = "0禁用 1启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

}
