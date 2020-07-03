package com.juchia.tutor.business.back.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.back.entity.bo.TeacherBO1;
import com.juchia.tutor.business.back.entity.query.TeacherQuery1;
import com.juchia.tutor.business.common.entity.dto.TeacherDTO;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.TeacherMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BackTeacherService extends ServiceImpl<TeacherMapper, Teacher> {

    public MyPage<TeacherDTO> pageDTO(PageQuery pageQuery, TeacherQuery1 teacherQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<TeacherDTO> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<TeacherDTO> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(teacherQuery.getRealname())){
            queryWrapper.like("realname",teacherQuery.getRealname());
        }
        if(teacherQuery.getState()!=null){
            queryWrapper.like("state",teacherQuery.getState());
        }

//      3调用Mapper
        IPage<TeacherDTO> teacherDTOIPage = getBaseMapper().selectPageDTO(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<TeacherDTO> myPage = BeanCopyUtils.copyBean(teacherDTOIPage, MyPage.class);
        myPage.setPages(teacherDTOIPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

//    这里的id直接teacherId 而不是 userId
    public Teacher audit(Long id, TeacherBO1 teacherBO) {
        Teacher teacher = getBaseMapper().selectById(id);
        if(teacher==null){
            throw new BusinessException("参数错误");
        }

        if(teacherBO.getState()==null){
            throw new BusinessException("参数错误");
        }

        if(teacherBO.getState().compareTo(2)==0){ //失败
            teacher.setState(2);
            if(StringUtils.isNotBlank(teacherBO.getReason())){
                teacher.setReason(teacherBO.getReason());
            }
        }else if(teacherBO.getState().compareTo(3)==0){ //通过
            teacher.setState(3);
        }else {
            throw new BusinessException("参数错误");
        }

        getBaseMapper().updateById(teacher);

        return teacher;
    }
}
