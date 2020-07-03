package com.juchia.tutor.business.auth.controller;


import com.juchia.tutor.business.auth.entity.bo.StudentAuthBO1;
import com.juchia.tutor.business.auth.entity.vo.StudentAuthVO;
import com.juchia.tutor.business.auth.service.AuthStudentAuthService;
import com.juchia.tutor.business.common.entity.po.StudentAuth;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 大学生认证 前端控制器
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@RestController
@RequestMapping("auth/studentAuths/me")
public class AuthStudentAuthController {

    @Autowired
    AuthStudentAuthService authStudentAuthService;

    @GetMapping
    @ApiOperation(value="获取我的大学生认证信息")
    public StudentAuthVO getStudentAuthForTeacher(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        StudentAuth studentAuth = null;
        try {
            studentAuth = authStudentAuthService.getStudentAuthForTeacher(id);
        } catch (ResourceNotFondException e) {
            e.printStackTrace();
        }
        return BeanCopyUtils.copyBean(studentAuth,StudentAuthVO.class);
    }

    @PutMapping
    @ApiOperation(value="新增或更新我的大学生认证信息")
    public StudentAuthVO saveOrUpdateStudentAuthForTeacher(@RequestBody StudentAuthBO1 studentAuthBO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        StudentAuth studentAuth = authStudentAuthService.saveOrUpdateStudentAuthForTeacher(id,studentAuthBO);
        return BeanCopyUtils.copyBean(studentAuth,StudentAuthVO.class);
    }

}

