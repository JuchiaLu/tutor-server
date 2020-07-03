package com.juchia.tutor.business.back.controller;

import com.juchia.tutor.business.back.entity.bo.TeacherBO1;
import com.juchia.tutor.business.back.entity.query.TeacherQuery1;
import com.juchia.tutor.business.back.entity.vo.TeacherVO;
import com.juchia.tutor.business.back.service.BackTeacherService;
import com.juchia.tutor.business.common.entity.dto.TeacherDTO;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("back/teachers")
@Api("老师接口")
public class BackTeacherController {

    @Autowired
    BackTeacherService backTeacherService;

    @ApiOperation(value="分页老师列表",notes="可以带查询条件和排序字段")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:teacher:select')")
    @GetMapping("page")
    public MyPage<TeacherVO> page(PageQuery pageQuery, TeacherQuery1 teacherQuery){
        MyPage<TeacherDTO> pageTeacherDTO = backTeacherService.pageDTO(pageQuery, teacherQuery);
        List<TeacherDTO> teacherDTOs = pageTeacherDTO.getRecords();

//        转换成我们的分页对象
        MyPage<TeacherVO> pageTeacherVO = BeanCopyUtils.copyBean(pageTeacherDTO,MyPage.class);

//        将DTO转换成VO
        List<TeacherVO> teacherVOs = BeanCopyUtils.copyList(teacherDTOs, TeacherVO.class);
        pageTeacherVO.setRecords(teacherVOs);
        return pageTeacherVO;
    }


    @ApiOperation(value="审核老师",notes="可以带查询条件和排序字段")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:teacher:update')")
    @PatchMapping("{id}/audit")
    public void   audit(@PathVariable("id") Long id, @RequestBody TeacherBO1 teacherBO){
        backTeacherService.audit(id, teacherBO);
    }


}
