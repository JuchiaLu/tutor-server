package com.juchia.tutor.system.front.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Data
@ApiModel(description = "路由")
public class RouteVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String componentPath;

    @ApiModelProperty(value = "排序")
    private Long weight;

    @ApiModelProperty(value = "状态",notes = "0禁用 1启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
