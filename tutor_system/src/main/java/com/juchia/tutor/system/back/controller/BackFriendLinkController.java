package com.juchia.tutor.system.back.controller;

import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.system.back.entity.bo.FriendLinkBO1;
import com.juchia.tutor.system.back.entity.vo.FriendLinkVO;
import com.juchia.tutor.system.back.service.BackFriendLinkService;
import com.juchia.tutor.system.common.entity.po.FriendLink;
import com.juchia.tutor.system.common.validate.group.Insert;
import com.juchia.tutor.system.common.validate.group.Update;
import com.juchia.tutor.system.front.entity.query.FriendLinkQuery1;
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
@RequestMapping("back/friendLinks")
@Api("友情链接")
public class BackFriendLinkController {


    @Autowired
    BackFriendLinkService backFriendLinkService;

    @ApiOperation(value="新添")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:friendLink:insert')")
    @PostMapping
    public FriendLinkVO save(@Validated(Insert.class) @RequestBody FriendLinkBO1 bo){
        FriendLink po = backFriendLinkService.mySave(bo);
        return BeanCopyUtils.copyBean(po,FriendLinkVO.class);
    }


    @ApiOperation(value="根据id删除")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:friendLink:delete')")
    @DeleteMapping("{id}")
    public void removeById(@PathVariable("id") Long id){
        backFriendLinkService.myRemoveById(id);
    }


    @ApiOperation(value="部分字段修改地区")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:friendLink:update')")
    @PatchMapping
    public void patchRoute(@Validated(Update.class) @RequestBody FriendLinkBO1 bo){
        backFriendLinkService.myPatch(bo);
    }


    @ApiOperation(value="分页")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:friendLink:select')")
    @GetMapping("page")
    public MyPage<FriendLinkVO> page(PageQuery pageQuery, FriendLinkQuery1 query){
        MyPage<FriendLink> pagePO = backFriendLinkService.myPage(pageQuery, query);
        List<FriendLink> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<FriendLinkVO> pageVO = BeanCopyUtils.copyBean(pagePO, MyPage.class);

//        将PO转换成VO
        List<FriendLinkVO> vos = BeanCopyUtils.copyList(records, FriendLinkVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }

}
