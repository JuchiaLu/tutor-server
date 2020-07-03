package com.juchia.tutor.system.back.controller;

import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.system.back.entity.bo.NavigationBO1;
import com.juchia.tutor.system.back.entity.vo.NavigationVO;
import com.juchia.tutor.system.back.service.BackNavigationService;
import com.juchia.tutor.system.common.entity.po.Navigation;
import com.juchia.tutor.system.common.validate.group.Insert;
import com.juchia.tutor.system.common.validate.group.Update;
import com.juchia.tutor.system.front.entity.query.NavigationQuery1;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("back/navigations")
@Api("导航接口")
public class BackNavigationController {


    @Autowired
    BackNavigationService backNavigationService;

    @ApiOperation(value="新添")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:navigation:insert')")
    @PostMapping
    public NavigationVO save(@Validated(Insert.class) @RequestBody NavigationBO1 bo){
        Navigation po = backNavigationService.mySave(bo);
        return BeanCopyUtils.copyBean(po,NavigationVO.class);
    }


    @ApiOperation(value="根据id删除")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:navigation:delete')")
    @DeleteMapping("{id}")
    public void removeById(@PathVariable("id") Long id){
        backNavigationService.myRemoveById(id);
    }


    @ApiOperation(value="部分字段修改地区")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:navigation:update')")
    @PatchMapping
    public void patchRoute(@Validated(Update.class) @RequestBody NavigationBO1 bo){
        backNavigationService.myPatch(bo);
    }


    @ApiOperation(value="分页")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:navigation:select')")
    @GetMapping("page")
    public MyPage<NavigationVO> page(PageQuery pageQuery, NavigationQuery1 query){
        MyPage<Navigation> pagePO = backNavigationService.myPage(pageQuery, query);
        List<Navigation> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<NavigationVO> pageVO = BeanCopyUtils.copyBean(pagePO, MyPage.class);

//        将PO转换成VO
        List<NavigationVO> vos = BeanCopyUtils.copyList(records, NavigationVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }

}
