package com.juchia.tutor.business.back.controller;


import com.juchia.tutor.business.auth.entity.vo.TeacherAuthVO;
import com.juchia.tutor.business.back.entity.bo.TeacherAuthBO1;
import com.juchia.tutor.business.back.entity.query.TeacherAuthQuery1;
import com.juchia.tutor.business.back.service.BackTeacherAuthService;
import com.juchia.tutor.business.common.entity.po.TeacherAuth;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 在职教师认证 前端控制器
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@RestController
@RequestMapping("back/teacherAuths")
public class BackTeacherAuthController {

    @Autowired
    BackTeacherAuthService backTeacherAuthService;



    @ApiOperation(value="分页在职教师认证列表",notes="可以带查询条件和排序字段")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:teacherAuth:select')")
    @GetMapping("page")
    public MyPage<TeacherAuthVO> page(PageQuery pageQuery, TeacherAuthQuery1 teacherAuthQuery){
        MyPage<TeacherAuth> pagePO = backTeacherAuthService.myPage(pageQuery, teacherAuthQuery);
        List<TeacherAuth> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<TeacherAuthVO> pageVO = BeanCopyUtils.copyBean(pagePO,MyPage.class);

//        将PO转换成VO
        List<TeacherAuthVO> vos = BeanCopyUtils.copyList(records, TeacherAuthVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }


    @ApiOperation(value="审核教师认证",notes="")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:teacherAuth:update')")
    @PatchMapping("{id}/audit")
    public void   audit(@PathVariable("id") Long id, @RequestBody TeacherAuthBO1 teacherAuthBO){
        backTeacherAuthService.audit(id, teacherAuthBO);
    }

}

