package com.juchia.tutor.system.front.controller;


import com.juchia.tutor.system.common.entity.po.Navigation;
import com.juchia.tutor.system.front.entity.query.NavigationQuery1;
import com.juchia.tutor.system.front.entity.vo.NavigationVO;
import com.juchia.tutor.system.front.service.NavigationService;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/navigations")
@Api("导航栏接口")
public class NavigationController {

    @Autowired
    NavigationService navigationService;

    @ApiOperation(value="获取导航栏列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping
    public List<NavigationVO> list(NavigationQuery1 navigationQuery){
        List<Navigation> navigations = navigationService.myList(navigationQuery);
        return BeanCopyUtils.copyList(navigations,NavigationVO.class);
    }
}
