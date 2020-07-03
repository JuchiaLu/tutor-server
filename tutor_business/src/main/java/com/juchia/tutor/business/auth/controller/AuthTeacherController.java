package com.juchia.tutor.business.auth.controller;

import com.juchia.tutor.business.auth.entity.bo.CashoutBO1;
import com.juchia.tutor.business.auth.entity.bo.TeacherBO1;
import com.juchia.tutor.business.auth.entity.vo.TeacherVO;
import com.juchia.tutor.business.auth.service.AuthTeacherService;
import com.juchia.tutor.business.common.entity.dto.TeacherDTO;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/teachers/me")
@Api("老师接口")
public class AuthTeacherController {

    @Autowired
    AuthTeacherService authTeacherService;

    @ApiOperation(value="获取我的信息")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping
    public TeacherVO getById( ) throws ResourceNotFondException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        TeacherDTO teacherDTO = authTeacherService.getDTOById(id);
        return BeanCopyUtils.copyBean(teacherDTO,TeacherVO.class);
    }

    @ApiOperation(value="部分字段更新我的信息")
    @ApiResponses({
         @ApiResponse(code=200,message="成功"),
    })
    @PatchMapping
    public TeacherVO patchById(@RequestBody TeacherBO1 teacherBO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        TeacherDTO teacherDTO = authTeacherService.patchById(id,teacherBO);
        return BeanCopyUtils.copyBean(teacherDTO,TeacherVO.class);
    }


    @ApiOperation(value="提现")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PatchMapping("cashout")
    public void cashout(@RequestBody CashoutBO1 cashoutBO1) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());
        authTeacherService.cashout(id,cashoutBO1);
    }
}
