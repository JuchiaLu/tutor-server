package com.juchia.tutor.system.front.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "首页list导航,")
public class NavigationVO implements Serializable {


    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 父id_外键
     */
    @ApiModelProperty(value = "父ID")
    private Long parentId;

    /**
     * 导航名字
     */
    @ApiModelProperty(value = "名字")
    private String name;

    /**
     * 导航路径
     */
    @ApiModelProperty(value = "导航路径")
    private String url;


    /**
     * 导航类型
     */
    @ApiModelProperty(value = "导航类型",notes = "1站内 2站外")
    private Integer type;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long weight;

    /**
     * 状态 0禁用 1启用
     */
    @ApiModelProperty(value = "状态",notes = "0禁用 1启用")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

}
