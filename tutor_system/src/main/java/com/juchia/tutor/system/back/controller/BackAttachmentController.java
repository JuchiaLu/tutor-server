package com.juchia.tutor.system.back.controller;

import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.system.back.entity.bo.AttachmentBO1;
import com.juchia.tutor.system.back.entity.query.AttachmentQuery1;
import com.juchia.tutor.system.back.entity.vo.AttachmentVO;
import com.juchia.tutor.system.back.service.BackAttachmentService;
import com.juchia.tutor.system.common.entity.po.Attachment;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("back/attachments")
@Api("附件接口")
public class BackAttachmentController {


    @Autowired
    BackAttachmentService backAttachmentService;


    @ApiOperation(value="上传附件",notes="")
    @ApiResponses({
            @ApiResponse(code =200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:attachment:upload')")
    @PostMapping("upload")
    public AttachmentVO upload(@RequestParam("file") MultipartFile file) throws IOException {
        Attachment attachment = backAttachmentService.myUpload(file);
        return BeanCopyUtils.copyBean(attachment,AttachmentVO.class);
    }

    @ApiOperation(value="新添")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:attachment:insert')")
    @PostMapping
    public AttachmentVO save(@Validated(Insert.class) @RequestBody AttachmentBO1 bo){
        Attachment po = backAttachmentService.mySave(bo);
        return BeanCopyUtils.copyBean(po,AttachmentVO.class);
    }


    @ApiOperation(value="根据id删除")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:attachment:delete')")
    @DeleteMapping("{id}")
    public void removeById(@PathVariable("id") Long id){
        backAttachmentService.myRemoveById(id);
    }


    @ApiOperation(value="部分字段修改地区")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:attachment:update')")
    @PatchMapping
    public void patchRoute(@Validated(Update.class) @RequestBody AttachmentBO1 bo){
        backAttachmentService.myPatch(bo);
    }


    @ApiOperation(value="分页")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:attachment:select')")
    @GetMapping("page")
    public MyPage<AttachmentVO> page(PageQuery pageQuery, AttachmentQuery1 query){
        MyPage<Attachment> pagePO = backAttachmentService.myPage(pageQuery, query);
        List<Attachment> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<AttachmentVO> pageVO = BeanCopyUtils.copyBean(pagePO, MyPage.class);

//        将PO转换成VO
        List<AttachmentVO> vos = BeanCopyUtils.copyList(records, AttachmentVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }




}
