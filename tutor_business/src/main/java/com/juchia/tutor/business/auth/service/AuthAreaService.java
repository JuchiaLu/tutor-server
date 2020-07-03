package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.common.entity.po.Area;
import com.juchia.tutor.business.common.entity.po.TeacherAreaRelation;
import com.juchia.tutor.business.common.mapper.AreaMapper;
import com.juchia.tutor.business.common.mapper.TeacherAreaRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class AuthAreaService extends ServiceImpl<AreaMapper, Area> {

    @Autowired
    TeacherAreaRelationMapper teacherAreaRelationMapper;

    public List<Area> listAreasForTeacher(Long id) {

        TeacherAreaRelation condition = new TeacherAreaRelation();
        condition.setTeacherId(id);
        List<Long> ids = teacherAreaRelationMapper.selectList(new QueryWrapper<>(condition))
                .stream().map(TeacherAreaRelation::getAreaId).collect(Collectors.toList());
        if(ids.size()==0){
            return new ArrayList<>(0);
        }

        return getBaseMapper().selectBatchIds(ids);
    }


    public List<Area> updateAreasForTeacher(Long id, List<Long> areasIds) {


        List<TeacherAreaRelation> teacherCategoryRelations = areasIds.stream().map(areaId->{
            TeacherAreaRelation teacherAreaRelation = new TeacherAreaRelation();
            teacherAreaRelation.setTeacherId(id);
            teacherAreaRelation.setAreaId(areaId);
            return teacherAreaRelation;
        }).collect(Collectors.toList());

        //先删除原来的
        TeacherAreaRelation condition = new TeacherAreaRelation();
        condition.setTeacherId(id);
        teacherAreaRelationMapper.delete(new QueryWrapper<>(condition));

        //重新插入
        teacherCategoryRelations.stream().forEach(teacherAreaRelationMapper::insert);

        return null;
    }

}
