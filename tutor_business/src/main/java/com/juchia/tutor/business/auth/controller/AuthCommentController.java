package com.juchia.tutor.business.auth.controller;


import com.juchia.tutor.business.auth.entity.bo.CommentBO1;
import com.juchia.tutor.business.auth.entity.query.CommentQuery1;
import com.juchia.tutor.business.auth.entity.vo.CommentVO;
import com.juchia.tutor.business.auth.service.AuthCommentService;
import com.juchia.tutor.business.common.entity.po.Comment;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("auth/comments/me")
public class AuthCommentController {

    @Autowired
    AuthCommentService authCommentService;

    @ApiOperation(value="分页条件查询我的评价(收到/发出)")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("page")
    public MyPage<CommentVO> pageCommentsForTeacher(PageQuery pageQuery, CommentQuery1 commentQuery){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        MyPage<Comment> myPagePO = authCommentService.pageCommentsForTeacher(pageQuery,commentQuery,id);
        List<Comment> records = myPagePO.getRecords();

        MyPage<CommentVO> myPageVO = BeanCopyUtils.copyBean(myPagePO,MyPage.class);

        List<CommentVO> commentVOs = BeanCopyUtils.copyList(records,CommentVO.class);
        myPageVO.setRecords(commentVOs);

        return myPageVO;
    }

    @ApiOperation(value="教师端写评论")
    @ApiResponses({
        @ApiResponse(code=200,message="成功"),
    })
    @PostMapping("{appointId}/save")
    public CommentVO saveCommentForTeacher(@RequestBody CommentBO1 commentBO, @PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Comment comment = authCommentService.saveCommentForTeacher(commentBO,id,appointId);

        CommentVO commentVO = BeanCopyUtils.copyBean(comment,CommentVO.class);

        return commentVO;
    }


    @ApiOperation(value="学生端写评论")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PostMapping("{appointId}/student/save")
    public CommentVO saveCommentForStudent(@RequestBody CommentBO1 commentBO, @PathVariable("appointId") Long appointId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Comment comment = authCommentService.saveCommentForStudent(commentBO,id,appointId);

        CommentVO commentVO = BeanCopyUtils.copyBean(comment,CommentVO.class);

        return commentVO;
    }

}

