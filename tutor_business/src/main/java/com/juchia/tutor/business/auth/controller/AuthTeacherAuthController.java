package com.juchia.tutor.business.auth.controller;


import com.juchia.tutor.business.auth.entity.bo.TeacherAuthBO1;
import com.juchia.tutor.business.auth.entity.vo.TeacherAuthVO;
import com.juchia.tutor.business.auth.service.AuthTeacherAuthService;
import com.juchia.tutor.business.common.entity.po.TeacherAuth;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 在职教师认证 前端控制器
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@RestController
@RequestMapping("auth/teacherAuths/me")
public class AuthTeacherAuthController {

    @Autowired
    AuthTeacherAuthService authTeacherAuthService;

    @ApiOperation(value="获取我的教师认证信息")
    @GetMapping
    public TeacherAuthVO getTeacherAuthForTeacher(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        TeacherAuth teacherAuth = null;
        try {
            teacherAuth = authTeacherAuthService.getTeacherAuthForTeacher(id);
        } catch (ResourceNotFondException e) {
            e.printStackTrace();
        }
        return  BeanCopyUtils.copyBean(teacherAuth,TeacherAuthVO.class);
    }

    @PutMapping
    @ApiOperation(value="新增或更新我的教师认证信息")
    public TeacherAuthVO saveOrUpdateTeacherAuthForTeacher(@RequestBody TeacherAuthBO1 teacherAuthBO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        TeacherAuth teacherAuth = authTeacherAuthService.saveOrUpdateTeacherAuthForTeacher(id,teacherAuthBO);
        return BeanCopyUtils.copyBean(teacherAuth,TeacherAuthVO.class);
    }

}

