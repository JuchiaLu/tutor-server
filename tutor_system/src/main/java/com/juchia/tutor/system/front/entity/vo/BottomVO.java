package com.juchia.tutor.system.front.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "网站底部信息")
public class BottomVO {

//    @ApiModelProperty(value = "版权信息")
//    private String copyright;
//
//    @ApiModelProperty(value = "公安备案号")
//    private String policeNumber;
//
//    @ApiModelProperty(value = "icp备案号")
//    private String icpNumber;
//
//    @ApiModelProperty(value = "联系电话")
//    private String phone;
//
//    @ApiModelProperty(value = "联系邮箱")
//    private String email;
//
//    @ApiModelProperty(value = "地址")
//    private String address;
    List<ConfigVO> config;
}
