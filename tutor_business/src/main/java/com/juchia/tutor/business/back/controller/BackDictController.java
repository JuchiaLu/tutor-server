package com.juchia.tutor.business.back.controller;

import com.juchia.tutor.business.back.entity.bo.DictBO1;
import com.juchia.tutor.business.back.entity.vo.DictVO;
import com.juchia.tutor.business.back.service.BackDictService;
import com.juchia.tutor.business.common.entity.po.Dict;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("back/dicts")
@Api("字典接口")
public class BackDictController {

    @Autowired
    BackDictService backDictService;

    @ApiOperation(value="根据字典码获取字典列表",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dict:select')")
    @GetMapping("code/{code}")
    public List<DictVO> listByCode(@PathVariable("code") String code){
        List<Dict> dicts = backDictService.listByCode(code);
        return BeanCopyUtils.copyList(dicts,DictVO.class);
    }


    @ApiOperation(value="根据字典类型的ID获取字典列表",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dict:select')")
    @GetMapping("dictTypeId/{dictTypeId}")
    public List<DictVO> listByDictTypeId(@PathVariable("dictTypeId") Long dictTypeId){
        List<Dict> dicts = backDictService.listByDictTypeId(dictTypeId);
        return BeanCopyUtils.copyList(dicts,DictVO.class);
    }


    @ApiOperation(value="增加字典",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dict:inset')")
    @PostMapping
    public DictVO save(@RequestBody DictBO1 dictBO){
        Dict dict = backDictService.mySave(dictBO);
        return BeanCopyUtils.copyBean(dict,DictVO.class);
    }


    @ApiOperation(value="部分字段修改字典",notes = "具体码写死请参考数据库")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dict:update')")
    @PatchMapping
    public DictVO update(@RequestBody DictBO1 dictBO){
        Dict dict = backDictService.myUpdate(dictBO);
        return BeanCopyUtils.copyBean(dict,DictVO.class);
    }




}
