package com.juchia.tutor.system.back.controller;

import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.system.back.entity.bo.NoticeBO1;
import com.juchia.tutor.system.back.entity.query.NoticeQuery1;
import com.juchia.tutor.system.back.entity.vo.NoticeVO;
import com.juchia.tutor.system.back.service.BackNoticeService;
import com.juchia.tutor.system.common.entity.po.Notice;
import com.juchia.tutor.system.common.validate.group.Insert;
import com.juchia.tutor.system.common.validate.group.Update;
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
@RequestMapping("back/notices")
@Api("建议接口")
public class BackNoticeController {


    @Autowired
    BackNoticeService backNoticeService;

    @ApiOperation(value="新添")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:notice:insert')")
    @PostMapping
    public NoticeVO save(@Validated(Insert.class) @RequestBody NoticeBO1 bo){
        Notice po = backNoticeService.mySave(bo);
        return BeanCopyUtils.copyBean(po,NoticeVO.class);
    }


    @ApiOperation(value="根据id删除")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:notice:delete')")
    @DeleteMapping("{id}")
    public void removeById(@PathVariable("id") Long id){
        backNoticeService.myRemoveById(id);
    }


    @ApiOperation(value="部分字段修改地区")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:notice:update')")
    @PatchMapping
    public void patchRoute(@Validated(Update.class) @RequestBody NoticeBO1 bo){
        backNoticeService.myPatch(bo);
    }


    @ApiOperation(value="分页")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:notice:select')")
    @GetMapping("page")
    public MyPage<NoticeVO> page(PageQuery pageQuery, NoticeQuery1 query){
        MyPage<Notice> pagePO = backNoticeService.myPage(pageQuery, query);
        List<Notice> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<NoticeVO> pageVO = BeanCopyUtils.copyBean(pagePO, MyPage.class);

//        将PO转换成VO
        List<NoticeVO> vos = BeanCopyUtils.copyList(records, NoticeVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }

}
