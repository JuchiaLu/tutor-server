package com.juchia.tutor.business.front.controller;

import com.juchia.tutor.business.common.entity.po.Area;
import com.juchia.tutor.business.front.entity.query.AreaQuery1;
import com.juchia.tutor.business.front.entity.vo.AreaVO;
import com.juchia.tutor.business.front.service.AreaService;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/areas")
@Api("地区接口")
public class AreaController {

    @Autowired
    AreaService areaService;


    @ApiOperation(value="获取地区列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping
    public List<AreaVO> list(AreaQuery1 areaQuery){
        List<Area> areas = areaService.Mylist(areaQuery);

        return BeanCopyUtils.copyList(areas, AreaVO.class);
    }


    @ApiOperation(value="根据父ID获取地区列表",notes = "0 代表顶级地区")
    @ApiImplicitParam(paramType="path", name = "id", value = "父ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("parentId/{id}")
    public List<AreaVO> listByParentId(@PathVariable("id") Long parentId){
        List<Area> areas = areaService.listByParentId(parentId);
        return BeanCopyUtils.copyList(areas, AreaVO.class);
    }

}
