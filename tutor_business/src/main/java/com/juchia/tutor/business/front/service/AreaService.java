package com.juchia.tutor.business.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.common.entity.po.Area;
import com.juchia.tutor.business.common.mapper.AreaMapper;
import com.juchia.tutor.business.front.entity.query.AreaQuery1;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService extends ServiceImpl<AreaMapper, Area> {
    public List<Area> Mylist(AreaQuery1 areaQuery) {
//        areaQuery 查询条件请根据业务需求写

        return getBaseMapper().selectList(new QueryWrapper<>());
    }

    public List<Area> listByParentId(Long parentId) {
        Area condition = new Area();
        condition.setParentId(parentId);
        return getBaseMapper().selectList(new QueryWrapper<>(condition));
    }

    public List<Area> listAreasForTeacher(Long id) {
        return null;
    }
}
