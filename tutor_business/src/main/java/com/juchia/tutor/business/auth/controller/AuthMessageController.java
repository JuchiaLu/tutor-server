package com.juchia.tutor.business.auth.controller;


import com.juchia.tutor.business.auth.entity.bo.MessageBO1;
import com.juchia.tutor.business.auth.entity.query.MessageQuery1;
import com.juchia.tutor.business.auth.entity.vo.MessageVO;
import com.juchia.tutor.business.auth.service.AuthMessageService;
import com.juchia.tutor.business.common.entity.po.Message;
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
@RequestMapping("auth/messages/me")
public class AuthMessageController {

    @Autowired
    AuthMessageService authMessageService;

    @ApiOperation(value="分页获取我的消息")
    @GetMapping("page")
    public MyPage<MessageVO> pageMessage(PageQuery pageQuery, MessageQuery1 messageQuery1){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        MyPage<Message> myPage = authMessageService.myPage(id,pageQuery,messageQuery1);
        List<Message> records = myPage.getRecords();

        List<MessageVO> voS = BeanCopyUtils.copyList(records, MessageVO.class);
        MyPage<MessageVO> myPage1 = BeanCopyUtils.copyBean(myPage, MyPage.class);

        myPage1.setRecords(voS);

        return myPage1;
    }


    @ApiOperation(value="标记消息为已读")
    @PatchMapping("{messageId}/read")
    public MessageVO readMessage(@PathVariable("messageId") Long messageId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Message message = authMessageService.readMessage(id,messageId);
        return BeanCopyUtils.copyBean(message, MessageVO.class);
    }

    @ApiOperation(value="根据id删除消息")
    @DeleteMapping("{messageId}")
    public MessageVO deleteMessage(@PathVariable("messageId") Long messageId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Message message = authMessageService.deleteMessage(id,messageId);
        return BeanCopyUtils.copyBean(message, MessageVO.class);
    }


    @ApiOperation(value="发送消息")
    @PostMapping
    public MessageVO saveMessage(@RequestBody MessageBO1 messageBO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Message message = authMessageService.saveMessage(id,messageBO);
        return BeanCopyUtils.copyBean(message, MessageVO.class);
    }

    @ApiOperation(value="获取我的未读消息条数")
    @GetMapping("countNotReadMessage")
    public int countNotReadMessage(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        int count = authMessageService.countNotReadMessage(id);
        return count;
    }

}

