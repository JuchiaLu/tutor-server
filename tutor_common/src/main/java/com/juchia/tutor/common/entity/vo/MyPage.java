package com.juchia.tutor.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@ApiModel(description = "分页")
public class MyPage<T> implements Serializable {

    /**
     * 查询数据列表
     */
    @ApiModelProperty(value = "记录")
    private List<T> records = Collections.emptyList();

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    private long total = 0;

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示条数")
    private long size = 10;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private long current = 1;

    /**
     * 总共页数
     */
    @ApiModelProperty(value = "总共页数")
    private long pages = 0;

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
