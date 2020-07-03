package com.juchia.tutor.business.front.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.common.entity.dto.TeacherDTO;
import com.juchia.tutor.business.common.entity.po.*;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.juchia.tutor.business.common.mapper.*;
import com.juchia.tutor.business.front.entity.query.TeacherQuery1;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {

    @Autowired
    TeacherAreaRelationMapper teacherAreaRelationMapper;

    @Autowired
    TeacherCategoryRelationMapper teacherCategoryRelationMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    AreaMapper areaMapper;



    public MyPage<TeacherDTO> pageDTO(PageQuery pageQuery, TeacherQuery1 teacherQuery) {
//      1 将我们的分页对象转换成mybatis plus 的
        Page<TeacherDTO> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<TeacherDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",3);// 审核通过的才显示
        if(teacherQuery.getCityId()!=null){
            queryWrapper.eq("city_id",teacherQuery.getCityId());
        }

        Set<Long> teacherIds = new HashSet<>();
        if(teacherQuery.getTeachAreaId()!=null){
            // 根据地区id查中间表得全部教员id,注意地区的父子关系要前台插入是配合,选子必选父
            TeacherAreaRelation teacherAreaRelation = new TeacherAreaRelation();
            teacherAreaRelation.setAreaId(teacherQuery.getTeachAreaId());
            teacherIds  = teacherAreaRelationMapper.selectList(new QueryWrapper<>(teacherAreaRelation))
                    .stream().map(TeacherAreaRelation::getTeacherId).collect(Collectors.toSet());
        }

        Set<Long> teacherIds2 = new HashSet<>();
        if(teacherQuery.getTeachCategoryId()!=null){
            // 根据分类id查中间表得全部教员id,注意分类的父子关系要前台插入是配合,选子必选父
            TeacherCategoryRelation categoryRelation = new TeacherCategoryRelation();
            categoryRelation.setCategoryId(teacherQuery.getTeachCategoryId());
            teacherIds2 = teacherCategoryRelationMapper.selectList(new QueryWrapper<>(categoryRelation))
                    .stream().map(TeacherCategoryRelation::getTeacherId).collect(Collectors.toSet());
        }

//        两者都传取交集
        if(teacherQuery.getTeachAreaId()!=null && teacherQuery.getTeachCategoryId()!=null){
            teacherIds.retainAll(teacherIds2);
            if(teacherIds.size()==0){
                MyPage<TeacherDTO> myPage = new MyPage<>();
                myPage.setCurrent(pageQuery.getCurrent());
                myPage.setSize(pageQuery.getSize());
                return myPage;
            }
            queryWrapper.in("user_id",teacherIds);
//            只传一者取并集
        }else if(teacherQuery.getTeachAreaId()!=null || teacherQuery.getTeachCategoryId()!=null){
            teacherIds.addAll(teacherIds2);
            if(teacherIds.size()==0){
                MyPage<TeacherDTO> myPage = new MyPage<>();
                myPage.setCurrent(pageQuery.getCurrent());
                myPage.setSize(pageQuery.getSize());
                return myPage;
            }
            queryWrapper.in("user_id",teacherIds);
        }
//            两者都不传不用管


        if(teacherQuery.getRealnameAuth()!=null){
            queryWrapper.eq("realname_auth",teacherQuery.getRealnameAuth());
        }
        if(teacherQuery.getStudentAuth()!=null){
            queryWrapper.eq("student_auth",teacherQuery.getStudentAuth());
        }
        if(teacherQuery.getTeacherAuth()!=null){
            queryWrapper.eq("teacher_auth",teacherQuery.getTeacherAuth());
        }



        if(StringUtils.isNotBlank(teacherQuery.getSort())){
            if(StringUtils.equals("totalSuccess",teacherQuery.getSort())){
                queryWrapper.orderBy(true,StringUtils.equals("asc",teacherQuery.getOrder()),"total_success");
            }else if(StringUtils.equals("satisfaction",teacherQuery.getSort())){
                queryWrapper.orderBy(true,StringUtils.equals("asc",teacherQuery.getOrder()),"satisfaction");
            }else if(StringUtils.equals("hourPrice",teacherQuery.getSort())){
                queryWrapper.orderBy(true,StringUtils.equals("asc",teacherQuery.getOrder()),"hour_price");
            }
        }

//      3调用Mapper
        IPage<TeacherDTO> teacherDTOIPage = getBaseMapper().selectPageDTO(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<TeacherDTO> myPage = BeanCopyUtils.copyBean(teacherDTOIPage, MyPage.class);
        myPage.setPages(teacherDTOIPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    public TeacherDTO getDTOById(Long id) throws ResourceNotFondException {
//        这里传进来的是 userId  不是 teacherId
        Teacher condition = new Teacher();
        condition.setUserId(id);
        Teacher teacher = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(teacher==null){
            throw new ResourceNotFondException("资源不存在");
        }
        TeacherDTO teacherDTO = getBaseMapper().selectDTOById(teacher.getId());
        return teacherDTO;
    }

    public MyPage<Comment> pageCommentsForTeacher(PageQuery pageQuery, Long id) {
//        这里传进来的是userId  不是 teacherId

//      1 将我们的分页对象转换成mybatis plus 的
        Page<Comment> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//       2查询条件
        Comment condition = new Comment();
        condition.setToId(id);
        IPage<Comment> commentIPage = commentMapper.selectPage(page, new QueryWrapper<>(condition));

//        3将mybatis plus 分页对象转换成我们的
        MyPage<Comment> myPage = BeanCopyUtils.copyBean(commentIPage, MyPage.class);
        myPage.setPages(commentIPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    public List<Category> listCategoriesForTeacher(Long id) {
//        这里传进来的是 userId  不是 teacherId
        TeacherCategoryRelation condition = new TeacherCategoryRelation();
        condition.setTeacherId(id);
        List<Long> ids = teacherCategoryRelationMapper.selectList(new QueryWrapper<>(condition))
                .stream().map(TeacherCategoryRelation::getCategoryId).collect(Collectors.toList());
        if(ids.size()==0){
            return new ArrayList<>(0);
        }
        return categoryMapper.selectBatchIds(ids);
    }

    public List<Area> listAreasForTeacher(Long id) {

//        这里传进来的是 userId  不是 teacherId
        TeacherAreaRelation condition = new TeacherAreaRelation();
        condition.setTeacherId(id);
        List<Long> ids = teacherAreaRelationMapper.selectList(new QueryWrapper<>(condition))
                .stream().map(TeacherAreaRelation::getAreaId).collect(Collectors.toList());
        if(ids.size()==0){
            return new ArrayList<>(0);
        }

        return areaMapper.selectBatchIds(ids);
    }
}
