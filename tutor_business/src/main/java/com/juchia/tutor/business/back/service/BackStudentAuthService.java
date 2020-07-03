package com.juchia.tutor.business.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.back.entity.bo.StudentAuthBO1;
import com.juchia.tutor.business.back.entity.query.StudentAuthQuery1;
import com.juchia.tutor.business.common.entity.po.StudentAuth;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.StudentAuthMapper;
import com.juchia.tutor.business.common.mapper.TeacherMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 大学生认证 服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class BackStudentAuthService extends ServiceImpl<StudentAuthMapper, StudentAuth>{


    @Autowired
    private TeacherMapper teacherMapper;

    public MyPage<StudentAuth> myPage(PageQuery pageQuery, StudentAuthQuery1 studentAuthQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<StudentAuth> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<StudentAuth> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(studentAuthQuery.getRealname())){
            queryWrapper.like("realname",studentAuthQuery.getRealname());
        }
        if(studentAuthQuery.getState()!=null){
            queryWrapper.eq("state",studentAuthQuery.getState());
        }

        //      3调用Mapper
        IPage<StudentAuth> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<StudentAuth> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    public void audit(Long id, StudentAuthBO1 studentAuthBO) {
        StudentAuth studentAuth = getBaseMapper().selectById(id);
        if(studentAuth==null){
            throw new BusinessException("参数错误");
        }

        if(studentAuthBO.getState()==null){
            throw new BusinessException("参数错误");
        }

        Teacher condition = new Teacher();
        condition.setUserId(studentAuth.getTeacherId());
        Teacher teacher = teacherMapper.selectOne(new QueryWrapper<>(condition));

        if(studentAuthBO.getState().compareTo(2)==0){
            studentAuth.setState(2); //认证失败
            teacher.setStudentAuth(0); //未认证
            if(StringUtils.isNotBlank(studentAuthBO.getReason())){
                studentAuth.setReason(studentAuthBO.getReason());
            }
        }else if(studentAuthBO.getState().compareTo(3)==0){
            studentAuth.setState(3); //认证成功
            teacher.setStudentAuth(1); //已认证
        }else {
            throw new BusinessException("参数错误");
        }


        teacherMapper.updateById(teacher);

        getBaseMapper().updateById(studentAuth);
    }
}
