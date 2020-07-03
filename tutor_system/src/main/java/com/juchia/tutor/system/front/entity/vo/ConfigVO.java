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
@ApiModel(description = "配置")
public class ConfigVO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 键
     */
    @ApiModelProperty(value = "键")
    private String key;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private String value;

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
