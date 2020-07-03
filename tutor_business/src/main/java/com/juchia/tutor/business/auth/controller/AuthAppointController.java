package com.juchia.tutor.business.auth.controller;


import com.juchia.tutor.business.auth.entity.query.AppointQuery1;
import com.juchia.tutor.business.auth.entity.vo.AppointVO;
import com.juchia.tutor.business.auth.service.AuthAppointService;
import com.juchia.tutor.business.common.entity.po.Appoint;
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
 * 老师预约表 前端控制器
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@RestController
@RequestMapping("auth/appoints/me")
public class AuthAppointController {

    @Autowired
    AuthAppointService authAppointService;

    @ApiOperation(value="老师端分页获取我的家教(预约)")
    @GetMapping("page")
    public MyPage<AppointVO> pageAppoint(PageQuery pageQuery, AppointQuery1 appointQuery){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        MyPage<Appoint> myPage = authAppointService.myPage(id,pageQuery,appointQuery);
        List<Appoint> records = myPage.getRecords();

        List<AppointVO> needVOS = BeanCopyUtils.copyList(records, AppointVO.class);
        MyPage<AppointVO> myPage1 = BeanCopyUtils.copyBean(myPage,MyPage.class);

        myPage1.setRecords(needVOS);

        return myPage1;
    }

    @ApiOperation(value="老师端为我添加预约")
    @PostMapping("{needId}")
    public AppointVO saveAppointForTeacher(@PathVariable("needId") Long needId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.saveAppointForTeacher(id,needId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }


    @ApiOperation(value="老师端为我取消预约")
    @PatchMapping("{appointId}/cancel")
    public AppointVO  cancelAppointForTeacher(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.cancelAppointForTeacher(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

    @ApiOperation(value="老师端结课操作")
    @PatchMapping("{appointId}/endCourse")
    public AppointVO  endCourseAppointForTeacher(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.endCourseAppointForTeacher(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

    @ApiOperation(value="老师端维权操作")
    @PatchMapping("{appointId}/right")
    public AppointVO  rightAppointForTeacher(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.rightAppointForTeacher(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

    @ApiOperation(value="老师端同意关闭") //需要调用支付服务,退款
    @PatchMapping("{appointId}/agreeClose")
    public AppointVO  agreeCloseAppointForTeacher(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.agreeCloseAppointForTeacher(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

    @ApiOperation(value="老师端拒绝关闭")
    @PatchMapping("{appointId}/rejectClose")
    public AppointVO  rejectCloseAppointForTeacher(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.rejectCloseAppointForTeacher(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }


    @ApiOperation(value="老师端删除操作")
    @DeleteMapping("{appointId}/teacher")
    public AppointVO  deleteAppointForTeacher(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.deleteAppointForTeacher(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }


//    -----------------------------------------------------------------------------------


    @ApiOperation(value="学生端获取支付宝付款跳转地址") //需要调用支付服务
    @GetMapping("{appointId}/student/payRedirectUrl")
    public String getPayRedirectUrl(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        String url = authAppointService.getPayRedirectUrl(id,appointId);
        return url;
    }


    @ApiOperation(value="学生端分页获取我的订单(预约)")
    @GetMapping("student/page")
    public MyPage<AppointVO> pageStudentAppoint(PageQuery pageQuery, AppointQuery1 appointQuery){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        MyPage<Appoint> myPage = authAppointService.myStudentPage(id,pageQuery,appointQuery);
        List<Appoint> records = myPage.getRecords();

        List<AppointVO> needVOS = BeanCopyUtils.copyList(records, AppointVO.class);
        MyPage<AppointVO> myPage1 = BeanCopyUtils.copyBean(myPage,MyPage.class);

        myPage1.setRecords(needVOS);

        return myPage1;
    }

    @ApiOperation(value="学生端取消某个老师的预约")
    @PatchMapping("student/{appointId}/cancel")
    public AppointVO  cancelAppointForStudent(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.cancelAppointForStudent(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

    @ApiOperation(value="学生端取消付款(关闭订单)")//需要调用支付服务
    @PatchMapping("{appointId}/student/close")
    public AppointVO closeOrder(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.closeOrder(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }


    @ApiOperation(value="学生端试教通过")
    @PatchMapping("{appointId}/student/pass")
    public AppointVO passOrder(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.passOrder(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

    @ApiOperation(value="学生端试教不通过") //需要调用支付服务,退款
    @PatchMapping("{appointId}/student/notPass")
    public AppointVO notPassOrder(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.notPassOrder(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

    //   主动结课
    @ApiOperation(value="学生端主动结课")
    @PatchMapping("{appointId}/student/endCourse")
    public AppointVO endCourse(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.endCourse(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

//   申请关闭
    @ApiOperation(value="学生端申请关闭")
    @PatchMapping("{appointId}/student/closeCourse")
    public AppointVO closeCourse(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.closeCourse(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

//    同意结课
    @ApiOperation(value="学生端同意结课")
    @PatchMapping("{appointId}/student/agreeCloseCourse")
    public AppointVO agreeCloseCourse(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.agreeCloseCourse(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

//    拒绝结课
    @ApiOperation(value="学生端拒绝结课")
    @PatchMapping("{appointId}/student/rejectCloseCourse")
    public AppointVO rejectCloseCourse(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.rejectCloseCourse(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }


//    软删除
    @ApiOperation(value="学生端软删除")
    @DeleteMapping("{appointId}/student")
    public AppointVO deleteAppointForStudent(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.deleteAppointForStudent(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

//    维权
    @ApiOperation(value="学生端维权操作")
    @PatchMapping("{appointId}/student/right")
    public AppointVO  rightAppointForStudent(@PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Appoint appoint = authAppointService.rightAppointForStudent(id,appointId);
        return BeanCopyUtils.copyBean(appoint,AppointVO.class);
    }

}

