package com.juchia.tutor.business.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.back.entity.bo.TeacherAuthBO1;
import com.juchia.tutor.business.back.entity.query.TeacherAuthQuery1;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.business.common.entity.po.TeacherAuth;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.TeacherAuthMapper;
import com.juchia.tutor.business.common.mapper.TeacherMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 在职教师认证 服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class BackTeacherAuthService extends ServiceImpl<TeacherAuthMapper, TeacherAuth> {


    @Autowired
    private TeacherMapper teacherMapper;


    public MyPage<TeacherAuth> myPage(PageQuery pageQuery, TeacherAuthQuery1 teacherAuthQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<TeacherAuth> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<TeacherAuth> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(teacherAuthQuery.getRealname())){
            queryWrapper.like("realname",teacherAuthQuery.getRealname());
        }
        if(teacherAuthQuery.getState()!=null){
            queryWrapper.eq("state",teacherAuthQuery.getState());
        }

        //      3调用Mapper
        IPage<TeacherAuth> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<TeacherAuth> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    public void audit(Long id, TeacherAuthBO1 teacherAuthBO) {
        TeacherAuth teacherAuth = getBaseMapper().selectById(id);
        if(teacherAuth==null){
            throw new BusinessException("参数错误");
        }

        if(teacherAuthBO.getState()==null){
            throw new BusinessException("参数错误");
        }

        Teacher condition = new Teacher();
        condition.setUserId(teacherAuth.getTeacherId());
        Teacher teacher = teacherMapper.selectOne(new QueryWrapper<>(condition));

        if(teacherAuthBO.getState().compareTo(2)==0){
            teacherAuth.setState(2); //失败
            teacher.setTeacherAuth(0); //未认证
            if(StringUtils.isNotBlank(teacherAuthBO.getReason())){
                teacherAuth.setReason(teacherAuthBO.getReason());
            }
        }else if(teacherAuthBO.getState().compareTo(3)==0){
            teacherAuth.setState(3); //通过
            teacher.setTeacherAuth(1); //已认证
        }else {
            throw new BusinessException("参数错误");
        }


        teacherMapper.updateById(teacher);

        getBaseMapper().updateById(teacherAuth);
    }
}
