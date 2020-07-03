package com.juchia.tutor.common.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "分页Query")
public class PageQuery implements Serializable {

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页条数",notes = "默认10")
    private long size = 10;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页",notes ="默认1" )
    private long current = 1;

//    /**
//     * 升序还是降序 asc 或 desc
//     */
//    @ApiModelProperty(value = "升序还是降序",notes ="升asc 或 降desc" )
//    private String order;
//
//    /**
//     * 要排序的字段
//     */
//    @ApiModelProperty(value = "要排序的字段")
//    private String sort;

}
