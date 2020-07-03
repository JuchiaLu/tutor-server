package com.juchia.tutor.business.back.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "评论")
public class CommentVO implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 学生id_外键(用户) 评论人
     */
    @ApiModelProperty(value = "学生id_外键",notes ="评论人" )
    private Long fromId;

    /**
     * 老师id_外键(用户) 评论给谁
     */
    @ApiModelProperty(value = "老师id_外键",notes = "评论给谁")
    private Long toId;

    /**
     * 需求id_外键 评论给哪个需求
     */
    @ApiModelProperty(value = "求id_外键",notes = "评论给哪个需求")
    private Long needId;

    /**
     * 父id_外键 预留
     */
    @ApiModelProperty("父id_外键")
    private Long parentId;

    /**
     * 预约id_外键
     */
    @ApiModelProperty("预约id_外键")
    private Long appointId;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String content;


    /**
     * 类型 1好评 2中评 3差评
     */
    @ApiModelProperty(value = "类型",notes = "1好评 2中评 3差评")
    private Integer type;

    /**
     * 评分等级 1-5
     */
    @ApiModelProperty(value = "评分等级",notes = "1-5")
    private Integer rank;

    /**
     * 评分标题 预留
     */
    @ApiModelProperty("评分标题")
    private String title;

    /**
     * 标签 空格隔开 预留
     */
    @ApiModelProperty(value = "标签",notes = "空格隔开")
    private String tag;

    /**
     * 状态 0禁用 1启用
     */
    @ApiModelProperty(value = "状态",notes = "0禁用 1启用")
    private Integer status;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Long weight;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
