package com.juchia.tutor.system.front.controller;

import com.juchia.tutor.system.front.entity.vo.BottomVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configs")
@Api("配置接口")
public class ConfigController {

    @ApiOperation(value="获取底部版权等信息",notes="")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("bottom")
    public BottomVO get(){
        return null;
    }

}
