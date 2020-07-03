package com.juchia.tutor.business.back.controller;


import com.juchia.tutor.business.back.entity.bo.RealnameAuthBO1;
import com.juchia.tutor.business.back.entity.query.RealnameAuthQuery1;
import com.juchia.tutor.business.back.entity.vo.RealnameAuthVO;
import com.juchia.tutor.business.back.service.BackRealnameAuthService;
import com.juchia.tutor.business.common.entity.po.RealnameAuth;
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
 * 实名认证 前端控制器
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@RestController
@RequestMapping("back/realnameAuths")
public class BackRealnameAuthController {

    @Autowired
    BackRealnameAuthService backRealnameAuthService;



    @ApiOperation(value="分页实名认证列表",notes="可以带查询条件和排序字段")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:realnameAuth:select')")
    @GetMapping("page")
    public MyPage<RealnameAuthVO> page(PageQuery pageQuery, RealnameAuthQuery1 realnameAuthQuery){
        MyPage<RealnameAuth> pagePO = backRealnameAuthService.myPage(pageQuery, realnameAuthQuery);
        List<RealnameAuth> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<RealnameAuthVO> pageVO = BeanCopyUtils.copyBean(pagePO,MyPage.class);

//        将PO转换成VO
        List<RealnameAuthVO> vos = BeanCopyUtils.copyList(records, RealnameAuthVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }


    @ApiOperation(value="审核实名认证",notes="")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:realnameAuth:update')")
    @PatchMapping("{id}/audit")
    public void   audit(@PathVariable("id") Long id, @RequestBody RealnameAuthBO1 realnameAuthBO){
        backRealnameAuthService.audit(id, realnameAuthBO);
    }

}

