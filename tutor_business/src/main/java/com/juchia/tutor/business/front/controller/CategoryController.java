package com.juchia.tutor.business.front.controller;


import com.juchia.tutor.business.common.entity.po.Category;
import com.juchia.tutor.business.front.entity.query.CategoryQuery1;
import com.juchia.tutor.business.front.entity.vo.CategoryVO;
import com.juchia.tutor.business.front.service.CategoryService;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 学科表 前端控制器
 * </p>
 *
 * @author juchia
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/categories")
@Api("科目接口")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @ApiOperation(value="获取科目列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping
    public List<CategoryVO> list(CategoryQuery1 categoryQuery){
        List<Category> categories = categoryService.Mylist(categoryQuery);
        return BeanCopyUtils.copyList(categories, CategoryVO.class);
    }


    @ApiOperation(value="根据父ID获取科目列表",notes = "0 代表顶级科目")
    @ApiImplicitParam(paramType="path", name = "id", value = "父ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("parentId/{id}")
    public List<CategoryVO> listByParentId(@PathVariable("id") Long parentId){
        List<Category> categories = categoryService.listByParentId(parentId);
        return BeanCopyUtils.copyList(categories, CategoryVO.class);
    }


}

