package com.juchia.tutor.business.back.controller;

import com.juchia.tutor.business.back.entity.query.AppointQuery1;
import com.juchia.tutor.business.back.entity.vo.AppointVO;
import com.juchia.tutor.business.back.service.BackAppointService;
import com.juchia.tutor.business.common.entity.po.Appoint;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("back/appoints")
@Api("预约接口")
public class BackAppointController {

    @Autowired
    BackAppointService backAppointService;

    @ApiOperation(value="分页",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:appoint:select')")
    @GetMapping("page")
    public MyPage<AppointVO> page(PageQuery pageQuery, AppointQuery1 appointQuery){
        MyPage<Appoint> myPage = backAppointService.myPage(pageQuery,appointQuery);
        List<Appoint> records = myPage.getRecords();

        List<AppointVO> vos = BeanCopyUtils.copyList(records, AppointVO.class);
        MyPage<AppointVO> myPage1 = BeanCopyUtils.copyBean(myPage,MyPage.class);

        myPage1.setRecords(vos);

        return myPage1;
    }

}
