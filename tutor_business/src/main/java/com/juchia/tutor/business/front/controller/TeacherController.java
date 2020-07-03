package com.juchia.tutor.business.front.controller;

import com.juchia.tutor.business.common.entity.dto.TeacherDTO;
import com.juchia.tutor.business.common.entity.po.Area;
import com.juchia.tutor.business.common.entity.po.Category;
import com.juchia.tutor.business.common.entity.po.Comment;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.juchia.tutor.business.front.entity.query.TeacherQuery1;
import com.juchia.tutor.business.front.entity.vo.AreaVO;
import com.juchia.tutor.business.front.entity.vo.CategoryVO;
import com.juchia.tutor.business.front.entity.vo.CommentVO;
import com.juchia.tutor.business.front.entity.vo.TeacherVO;
import com.juchia.tutor.business.front.service.TeacherService;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@Api("老师接口")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @ApiOperation(value="分页老师列表",notes="可以带查询条件和排序字段")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("page")
    public MyPage<TeacherVO> page(PageQuery pageQuery, TeacherQuery1 teacherQuery){
        MyPage<TeacherDTO> pageTeacherDTO = teacherService.pageDTO(pageQuery, teacherQuery);
        List<TeacherDTO> teacherDTOs = pageTeacherDTO.getRecords();

//        转换成我们的分页对象
        MyPage<TeacherVO> pageTeacherVO = BeanCopyUtils.copyBean(pageTeacherDTO,MyPage.class);

//        将DTO转换成VO
        List<TeacherVO> teacherVOs = BeanCopyUtils.copyList(teacherDTOs,TeacherVO.class);
        pageTeacherVO.setRecords(teacherVOs);
        return pageTeacherVO;
    }


    @ApiOperation(value="根据ID获取老师信息")
    @ApiImplicitParam(paramType="path", name = "id", value = "ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("{id}")
    public TeacherVO getById(@PathVariable("id") Long id) throws ResourceNotFondException {
        TeacherDTO teacherDTO = teacherService.getDTOById(id);
        return BeanCopyUtils.copyBean(teacherDTO,TeacherVO.class);
    }


    @ApiOperation(value="根据id分页查询家长对老师的评价")
    @ApiImplicitParam(paramType="path", name = "id", value = "ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("{id}/comments/page")
    public MyPage<CommentVO> pageCommentsForTeacher(PageQuery pageQuery, @PathVariable Long id){
        MyPage<Comment> myPagePO = teacherService.pageCommentsForTeacher(pageQuery,id);
        List<Comment> records = myPagePO.getRecords();

        MyPage<CommentVO> myPageVO = BeanCopyUtils.copyBean(myPagePO,MyPage.class);

        List<CommentVO> commentVOs = BeanCopyUtils.copyList(records,CommentVO.class);
        myPageVO.setRecords(commentVOs);

        return myPageVO;
    }


    @ApiOperation(value="获取老师可授科目列表")
    @ApiImplicitParam(paramType="path", name = "id", value = "ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("{id}/categories")
    public List<CategoryVO> listCategoriesForTeacher(@PathVariable Long id){
        List<Category> categories = teacherService.listCategoriesForTeacher(id);
        return BeanCopyUtils.copyList(categories,CategoryVO.class);
    }


    @ApiOperation(value="获取老师可授地区列表")
    @ApiImplicitParam(paramType="path", name = "id", value = "ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("{id}/areas")
    public List<AreaVO> listAreasForTeacher(@PathVariable Long id){
        List<Area> categories = teacherService.listAreasForTeacher(id);
        return BeanCopyUtils.copyList(categories,AreaVO.class);
    }

}
