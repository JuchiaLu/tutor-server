package com.juchia.tutor.system.front.entity.vo;

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
@ApiModel(description = "首页page公告,")
public class NoticeVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;


    @ApiModelProperty(value = "内容")
    private String content;


    @ApiModelProperty(value = "排序")
    private Long weight;

    @ApiModelProperty(value = "状态",notes = "0禁用 1启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
}
