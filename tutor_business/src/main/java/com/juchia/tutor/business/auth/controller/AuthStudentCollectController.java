package com.juchia.tutor.business.auth.controller;


import com.juchia.tutor.business.auth.entity.vo.TeacherVO2;
import com.juchia.tutor.business.auth.service.AuthStudentCollectService;
import com.juchia.tutor.business.back.entity.query.TeacherQuery1;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@RestController
@RequestMapping("auth/studentCollects/me")
public class AuthStudentCollectController {


    @Autowired
    AuthStudentCollectService authStudentCollectService;

    @ApiOperation(value="分页获取我收藏的老师")
    @GetMapping("teachers/page")
    public MyPage<TeacherVO2> page(PageQuery pageQuery, TeacherQuery1 teacherQuery1){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        MyPage<Teacher> myPage = authStudentCollectService.myPage(id,pageQuery,teacherQuery1);
        List<Teacher> records = myPage.getRecords();

        List<TeacherVO2> voS = BeanCopyUtils.copyList(records, TeacherVO2.class);
        MyPage<TeacherVO2> myPage1 = BeanCopyUtils.copyBean(myPage, MyPage.class);

        myPage1.setRecords(voS);

        return myPage1;
    }

    @ApiOperation(value="收藏老师")
    @PostMapping("teacherId/{teacherId}")
    public void save(@PathVariable("teacherId") Long teacherId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());
        authStudentCollectService.mySave(id,teacherId);
    }


    @ApiOperation(value="取消收藏老师")
    @DeleteMapping("teacherId/{teacherId}")
    public void delete(@PathVariable("teacherId") Long teacherId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());
        authStudentCollectService.myDelete(id,teacherId);
    }

}

