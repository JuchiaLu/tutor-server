package com.juchia.tutor.system.front.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 地区表
 * </p>
 *
 * @author juchia
 * @since 2019-12-19
 */
@Data
@ApiModel(description = "首页list导航,")
public class NavigationQuery1 implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "导航路径")
    private String uri;

    @ApiModelProperty(value = "导航类型",notes = "0站内 1站外")
    private Integer type;

    @ApiModelProperty(value = "排序")
    private Long weight;

    @ApiModelProperty(value = "状态",notes = "0禁用 1启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

}
