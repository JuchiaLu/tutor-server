package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.common.entity.po.Category;
import com.juchia.tutor.business.common.entity.po.TeacherCategoryRelation;
import com.juchia.tutor.business.common.mapper.CategoryMapper;
import com.juchia.tutor.business.common.mapper.TeacherCategoryRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 学科表 服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class AuthCategoryService extends ServiceImpl<CategoryMapper, Category> {

    @Autowired
    TeacherCategoryRelationMapper teacherCategoryRelationMapper;

    public List<Category> listCategoriesForTeacher(Long id) {
        TeacherCategoryRelation condition = new TeacherCategoryRelation();
        condition.setTeacherId(id);
        List<Long> ids = teacherCategoryRelationMapper.selectList(new QueryWrapper<>(condition))
                .stream().map(TeacherCategoryRelation::getCategoryId).collect(Collectors.toList());
        if(ids.size()==0){
            return new ArrayList<>(0);
        }
        return getBaseMapper().selectBatchIds(ids);
    }

    public List<Category> updateCategoriesForTeacher(Long id, List<Long> categoriesIds) {


        List<TeacherCategoryRelation> teacherCategoryRelations = categoriesIds.stream().map(categoryId->{
            TeacherCategoryRelation teacherCategoryRelation = new TeacherCategoryRelation();
            teacherCategoryRelation.setTeacherId(id);
            teacherCategoryRelation.setCategoryId(categoryId);
            return teacherCategoryRelation;
        }).collect(Collectors.toList());

        //先删除原来的
        TeacherCategoryRelation condition = new TeacherCategoryRelation();
        condition.setTeacherId(id);
        teacherCategoryRelationMapper.delete(new QueryWrapper<>(condition));

        //重新插入
        teacherCategoryRelations.stream().forEach(teacherCategoryRelationMapper::insert);

        return null;
    }
}
