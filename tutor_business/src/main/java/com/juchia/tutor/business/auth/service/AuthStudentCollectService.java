package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.back.entity.query.TeacherQuery1;
import com.juchia.tutor.business.common.entity.po.StudentCollect;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.StudentCollectMapper;
import com.juchia.tutor.business.common.mapper.TeacherMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class AuthStudentCollectService extends ServiceImpl<StudentCollectMapper, StudentCollect> {

    @Autowired
    TeacherMapper teacherMapper;

    public MyPage<Teacher> myPage(Long id, PageQuery pageQuery, TeacherQuery1 teacherQuery1) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Teacher> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());


        // 根据id查中间表,获得全部老师id
        StudentCollect condition = new StudentCollect();
        condition.setStudentId(id);
        List<StudentCollect> studentCollects = getBaseMapper().selectList(new QueryWrapper<>(condition));
        List<Long> ids = studentCollects.stream().map(StudentCollect::getTeacherId).collect(Collectors.toList());



        if(ids.size()==0){
            MyPage<Teacher> teacherMyPage = new MyPage<>();
            teacherMyPage.setSize(pageQuery.getSize());
            teacherMyPage.setCurrent(pageQuery.getCurrent());
            return teacherMyPage;
        }


//      2构建QueryWrapper
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id",ids);


//        3调用Mapper
        IPage<Teacher> iPage = teacherMapper.selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<Teacher> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    public void mySave(Long id, Long teacherId) {

        if(id.compareTo(teacherId)==0){
            throw new BusinessException("不能收藏自己");
        }


        StudentCollect studentCollect = new StudentCollect();
        studentCollect.setStudentId(id);
        studentCollect.setTeacherId(teacherId);
        StudentCollect studentCollect1 = getBaseMapper().selectOne(new QueryWrapper<>(studentCollect));
        if(studentCollect1!=null){
            throw new BusinessException("重复收藏");
        }


        getBaseMapper().insert(studentCollect);
    }

    public void myDelete(Long id, Long teacherId) {
        StudentCollect studentCollect = new StudentCollect();
        studentCollect.setStudentId(id);
        studentCollect.setTeacherId(teacherId);

        StudentCollect studentCollect1 = getBaseMapper().selectOne(new QueryWrapper<>(studentCollect));
        if(studentCollect1==null){
            throw new BusinessException("未收藏");
        }
        getBaseMapper().delete(new QueryWrapper<>(studentCollect));
    }
}
