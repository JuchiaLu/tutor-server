package com.juchia.tutor.system.front.controller;


import com.juchia.tutor.system.common.entity.po.Dict;
import com.juchia.tutor.system.front.entity.vo.DictVO;
import com.juchia.tutor.system.front.service.DictService;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dicts")
@Api("字典接口")
public class DictController {

    @Autowired
    DictService dictService;

    @ApiOperation(value="根据字典码获取字典列表",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("code/{code}")
    public List<DictVO> listByCode(@PathVariable("code") String code){
        List<Dict> dicts = dictService.listByCode(code);
        return BeanCopyUtils.copyList(dicts,DictVO.class);
    }

}
