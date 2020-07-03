package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.auth.entity.bo.MessageBO1;
import com.juchia.tutor.business.auth.entity.query.MessageQuery1;
import com.juchia.tutor.business.common.entity.po.Message;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.MessageMapper;
import com.juchia.tutor.business.common.mapper.TeacherMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class AuthMessageService extends ServiceImpl<MessageMapper, Message> {


    @Autowired
    TeacherMapper teacherMapper;


    public MyPage<Message> myPage(Long id, PageQuery pageQuery, MessageQuery1 messageQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Message> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("to_id",id); //收到的消息
        if(messageQuery.getType()!=null){
            queryWrapper.eq("type",messageQuery.getType()); //系统消息或用户消息
        }

//        3调用Mapper
        IPage<Message> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<Message> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    public Message readMessage(Long id, Long messageId) {
        Message message = getBaseMapper().selectById(messageId);
        if(message==null){
            throw  new BusinessException("参数错误");
        }
        if(message.getToId().compareTo(id)!=0){
            throw  new BusinessException("无权操作");
        }
        message.setState(1); //已读
        getBaseMapper().updateById(message);

        return message;
    }

    public Message deleteMessage(Long id, Long messageId) {
        Message message = getBaseMapper().selectById(messageId);
        if(message==null){
            throw  new BusinessException("参数错误");
        }
        if(message.getToId().compareTo(id)!=0){
            throw  new BusinessException("无权操作");
        }
        getBaseMapper().deleteById(message);

        return message;
    }

    public Message saveMessage(Long id, MessageBO1 messageBO) {

        if(id.compareTo(messageBO.getToId())==0){
            throw new BusinessException("不允许向自己发消息");
        }

//      判断消息接收者是否存在
        Teacher condition = new Teacher();
        condition.setUserId(messageBO.getToId());
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>(condition);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        if(teacher==null){
            throw new BusinessException("消息接收者不存在");
        }


        Message message = BeanCopyUtils.copyBean(messageBO, Message.class);

        message.setFromId(id); //发送者
        message.setType(2); //消息类型 用户消息
        message.setState(0); //0未读 1已读
        message.setCreateTime(LocalDateTime.now());
        getBaseMapper().insert(message);

        return message;
    }

    public int countNotReadMessage(Long id) {
        Message condition = new Message();
        condition.setToId(id);
        condition.setState(0); //未读
        return getBaseMapper().selectCount(new QueryWrapper<>(condition));
    }
}
