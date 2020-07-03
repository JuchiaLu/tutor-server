package com.juchia.tutor.business.back.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author juchia
 * @since 2019-12-19
 */
@Data
@ApiModel(description = "字典")
public class DictBO1 implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 字典类型id_外键
     */
    private Long dictTypeId;

    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值",notes = "如 男")
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
