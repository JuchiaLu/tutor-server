package com.juchia.tutor.business.front.controller;

import com.juchia.tutor.business.common.entity.dto.NeedDTO;
import com.juchia.tutor.business.common.entity.dto.TeacherDTO;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.juchia.tutor.business.front.entity.query.NeedQuery1;
import com.juchia.tutor.business.front.entity.vo.NeedVO;
import com.juchia.tutor.business.front.entity.vo.TeacherVO;
import com.juchia.tutor.business.front.service.NeedService;
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
@RequestMapping("/needs")
@Api("需求接口")
public class NeedController {

    @Autowired
    NeedService needService;

    @ApiOperation(value="分页需求列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("page")
    public MyPage<NeedVO> page(PageQuery pageQuery, NeedQuery1 needQuery){
        MyPage<NeedDTO> myPage = needService.pageDTO(pageQuery,needQuery);
        List<NeedDTO> records = myPage.getRecords();

        List<NeedVO> needVOS = BeanCopyUtils.copyList(records, NeedVO.class);
        MyPage<NeedVO> myPage1 = BeanCopyUtils.copyBean(myPage,MyPage.class);

        myPage1.setRecords(needVOS);

        return myPage1;
    }

    @ApiOperation(value="根据ID获取需求信息")
    @ApiImplicitParam(paramType="path", name = "id", value = "ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("{id}")
    public NeedVO getById(@PathVariable("id") Long id) throws ResourceNotFondException {
        NeedDTO needDTO = needService.getDTOById(id);
        return BeanCopyUtils.copyBean(needDTO,NeedVO.class);
    }


    @ApiOperation(value="根据id分页查询已预约的老师")
    @ApiImplicitParam(paramType="path", name = "id", value = "ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("{id}/teachers/page")
    public MyPage<TeacherVO> pageTeachersForNeed(PageQuery pageQuery, @PathVariable Long id){

        MyPage<TeacherDTO> teacherMyPage = needService.pageTeachersForNeed(pageQuery,id);
        List<TeacherDTO> records = teacherMyPage.getRecords();

        List<TeacherVO> teacherVOs = BeanCopyUtils.copyList(records, TeacherVO.class);
        MyPage<TeacherVO> teacherVOMyPage = BeanCopyUtils.copyBean(teacherMyPage,MyPage.class);
        teacherVOMyPage.setRecords(teacherVOs);

        return teacherVOMyPage;
    }

}
