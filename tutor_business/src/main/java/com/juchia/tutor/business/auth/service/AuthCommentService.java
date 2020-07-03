package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.auth.entity.bo.CommentBO1;
import com.juchia.tutor.business.auth.entity.query.CommentQuery1;
import com.juchia.tutor.business.common.entity.po.Appoint;
import com.juchia.tutor.business.common.entity.po.Comment;
import com.juchia.tutor.business.common.entity.po.Need;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.AppointMapper;
import com.juchia.tutor.business.common.mapper.CommentMapper;
import com.juchia.tutor.business.common.mapper.NeedMapper;
import com.juchia.tutor.business.common.mapper.TeacherMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class AuthCommentService extends ServiceImpl<CommentMapper, Comment> {

    @Autowired
    AppointMapper appointMapper;

    @Autowired
    NeedMapper needMapper;

    @Autowired
    TeacherMapper teacherMapper;

    public MyPage<Comment> pageCommentsForTeacher(PageQuery pageQuery, CommentQuery1 commentQuery, Long id) {
//      1 将我们的分页对象转换成mybatis plus 的
        Page<Comment> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//       2查询条件
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if(commentQuery.getQueryType()!=null){
            if(commentQuery.getQueryType().compareTo(1)==0){ //收到
                queryWrapper.eq("to_id",id);
            } else if(commentQuery.getQueryType().compareTo(2)==0){ //发出
                queryWrapper.eq("from_id",id);
            }
        } else { //收到和发出的都算
            queryWrapper.eq("to_id",id);
            queryWrapper.or();
            queryWrapper.eq("from_id",id);
        }
        IPage<Comment> commentIPage = getBaseMapper().selectPage(page, queryWrapper);

//        3将mybatis plus 分页对象转换成我们的
        MyPage<Comment> myPage = BeanCopyUtils.copyBean(commentIPage, MyPage.class);
        myPage.setPages(commentIPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    //老师评价学生
    @Transactional
    public Comment saveCommentForTeacher(CommentBO1 commentBO, Long id, Long appointId) {

        Appoint appoint = appointMapper.selectById(appointId);
//       校验是否有权限
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getTeacherId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
//      判断状态是否正确
        if(appoint.getState().compareTo(7)!=0 || appoint.getTeacherCommentState().compareTo(0)!=0) { //已完成, 且老师未评价
            throw new BusinessException("状态错误");
        }


        appoint.setTeacherCommentState(1); //提前设置老师已评价
        Need need = needMapper.selectById(appoint.getNeedId());
        if(appoint.getTeacherCommentState().compareTo(1)==0 && appoint.getStudentCommentState().compareTo(1)==0){
//          如果已经互评, 修改需求状态为 已完成
            need.setState(5); //已完成
        }


//       新添评价
        Comment saving = new Comment();
        saving.setFromId(appoint.getTeacherId()); //评价人
        saving.setToId(appoint.getStudentId()); //被评价人
        saving.setAppointId(appoint.getId());
        saving.setNeedId(appoint.getNeedId());
        saving.setRank(commentBO.getRank());
        saving.setTag(commentBO.getTag());
        saving.setContent(commentBO.getContent());
        saving.setCreateTime(LocalDateTime.now());
        getBaseMapper().insert(saving);


//        更新需求的老师的评论id
        need.setTeacherCommentId(saving.getId());
        needMapper.updateById(need);

        appointMapper.updateById(appoint);

//      更新（学生）被评价条数
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("user_id",appoint.getStudentId());
        Teacher teacher = teacherMapper.selectOne(teacherQueryWrapper);
        teacher.setTotalComment(teacher.getTotalComment()+1);
        teacherMapper.updateById(teacher);

        return saving;
    }

    //学生评价老师
    @Transactional
    public Comment saveCommentForStudent(CommentBO1 commentBO, Long id, Long appointId) {
        Appoint appoint = appointMapper.selectById(appointId);
//       校验是否有权限
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
//      判断状态是否正确
        if(appoint.getState().compareTo(7)!=0 || appoint.getStudentCommentState().compareTo(0)!=0) { //已完成, 且学生未评论
            throw new BusinessException("状态错误");
        }
        appoint.setStudentCommentState(1); //提前设置学生评论状态为已评论
        Need need = needMapper.selectById(appoint.getNeedId());
        if(appoint.getTeacherCommentState().compareTo(1)==0 && appoint.getStudentCommentState().compareTo(1)==0){
//          如果已经互评, 修改需求状态为 已完成
            need.setState(5); //已完成
        }

//       新增评论
        Comment saving = new Comment();
        saving.setFromId(appoint.getStudentId()); //评论人
        saving.setToId(appoint.getTeacherId()); //被评论人
        saving.setAppointId(appoint.getId());
        saving.setNeedId(appoint.getNeedId());
        saving.setRank(commentBO.getRank());
        saving.setTag(commentBO.getTag());
        saving.setContent(commentBO.getContent());
        saving.setCreateTime(LocalDateTime.now());
        getBaseMapper().insert(saving);

//        更新需求的学生评论id
        need.setStudentCommentId(saving.getId());
        needMapper.updateById(need);

//       更新预约
        appointMapper.updateById(appoint);

 //      更新老师被评价条数
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("user_id",appoint.getTeacherId());
        Teacher teacher = teacherMapper.selectOne(teacherQueryWrapper);
        teacher.setTotalComment(teacher.getTotalComment()+1);
        teacherMapper.updateById(teacher);

        return saving;
    }
}
