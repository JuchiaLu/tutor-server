package com.juchia.tutor.business.auth.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "首页list科目")
public class CategoryVO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 父id_外键
     */
    @ApiModelProperty(value = "父ID")
    private Long parentId;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字")
    private String name;

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
