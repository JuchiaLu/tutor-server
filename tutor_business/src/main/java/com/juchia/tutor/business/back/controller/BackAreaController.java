package com.juchia.tutor.business.back.controller;

import com.juchia.tutor.business.back.entity.bo.AreaBO1;
import com.juchia.tutor.business.back.entity.query.AreaQuery1;
import com.juchia.tutor.business.back.entity.vo.AreaVO;
import com.juchia.tutor.business.back.service.BackAreaService;
import com.juchia.tutor.business.common.entity.po.Area;
import com.juchia.tutor.business.common.validate.group.Insert;
import com.juchia.tutor.business.common.validate.group.Update;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("back/areas")
@Api("地区接口")
public class BackAreaController {

    @Autowired
    BackAreaService backAreaService;

    @ApiOperation(value="获取地区列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:area:select')")
    @GetMapping
    public List<AreaVO> list(AreaQuery1 areaQuery){
        List<Area> areas = backAreaService.Mylist(areaQuery);
        return BeanCopyUtils.copyList(areas, AreaVO.class);
    }


    @ApiOperation(value="根据父ID获取地区列表",notes = "0 代表顶级地区")
    @ApiImplicitParam(paramType="path", name = "id", value = "父ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:area:select')")
    @GetMapping("parentId/{id}")
    public List<AreaVO> listByParentId(@PathVariable("id") Long parentId){
        List<Area> areas = backAreaService.listByParentId(parentId);
        return BeanCopyUtils.copyList(areas, AreaVO.class);
    }



    @ApiOperation(value="新添地区",notes = "0 代表顶级地区")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:area:insert')")
    @PostMapping
    public AreaVO save(@Validated(Insert.class) @RequestBody AreaBO1 areaBO){
        Area area = backAreaService.mySave(areaBO);
        return BeanCopyUtils.copyBean(area,AreaVO.class);
    }


    @ApiOperation(value="根据id删除地区",notes = "0 代表顶级地区")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:area:delete')")
    @DeleteMapping("{id}")
    public void removeRouteById(@PathVariable("id") Long id){
        backAreaService.myRemoveById(id);
    }


    @ApiOperation(value="部分字段修改地区",notes = "0 代表顶级地区")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:area:update')")
    @PatchMapping
    public void patchRoute(@Validated(Update.class) @RequestBody AreaBO1 areaBO1){
        backAreaService.myPatch(areaBO1);
    }


}
