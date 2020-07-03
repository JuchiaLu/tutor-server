package com.juchia.tutor.business.auth.controller;

import com.juchia.tutor.business.auth.entity.vo.AreaVO;
import com.juchia.tutor.business.auth.entity.vo.CategoryVO;
import com.juchia.tutor.business.auth.service.AuthAreaService;
import com.juchia.tutor.business.common.entity.po.Area;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth/areas/me")
@Api("地区接口")
public class AuthAreaController {

    @Autowired
    AuthAreaService authAreaService;

    @ApiOperation(value="获取我可授地区列表")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping
    public List<AreaVO> listAreasForTeacher(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        List<Area> categories = authAreaService.listAreasForTeacher(id);
        return BeanCopyUtils.copyList(categories,AreaVO.class);
    }

    @ApiOperation(value="覆盖更新我的可授地区列表")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PutMapping
    public List<CategoryVO> updateCategoriesForMe(@RequestBody List<Long> areasIds){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        List<Area> areas = authAreaService.updateAreasForTeacher(id,areasIds);;
        return BeanCopyUtils.copyList(areas,CategoryVO.class);
    }

}
