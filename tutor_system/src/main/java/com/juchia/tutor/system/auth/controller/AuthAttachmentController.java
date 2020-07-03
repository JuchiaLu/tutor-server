package com.juchia.tutor.system.auth.controller;

import com.juchia.tutor.system.auth.entity.vo.AttachmentVO;
import com.juchia.tutor.system.auth.service.AuthAttachmentService;
import com.juchia.tutor.system.common.entity.po.Attachment;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("auth/attachments")
@Api("附件接口")
public class AuthAttachmentController {


    @Autowired
    AuthAttachmentService authAttachmentService;

    @ApiOperation(value="上传附件",notes="")
    @ApiResponses({
            @ApiResponse(code =200,message="成功"),
    })
    @PostMapping
    public AttachmentVO save(@RequestParam("file") MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Attachment attachment = authAttachmentService.mySave(id,file);
        return BeanCopyUtils.copyBean(attachment,AttachmentVO.class);
    }

}
