package com.juchia.tutor.business.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.back.entity.bo.AreaBO1;
import com.juchia.tutor.business.back.entity.query.AreaQuery1;
import com.juchia.tutor.business.common.entity.po.Area;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.AreaMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackAreaService extends ServiceImpl<AreaMapper, Area> {
    public List<Area> Mylist(AreaQuery1 categoryQuery) {
        //        areaQuery 查询条件请根据业务需求写

        return getBaseMapper().selectList(new QueryWrapper<>());
    }

    public List<Area> listByParentId(Long parentId) {
        Area condition = new Area();
        condition.setParentId(parentId);
        return getBaseMapper().selectList(new QueryWrapper<>(condition));
    }

    public Area mySave(AreaBO1 areaBO) {

        Area area = BeanCopyUtils.copyBean(areaBO, Area.class);

        getBaseMapper().insert(area);

        return area;
    }

    public void myRemoveById(Long id) {
        Area condition = new Area();
        condition.setParentId(id);
        Integer count = getBaseMapper().selectCount(new QueryWrapper<>(condition));
        if(count.compareTo(0)!=0){
            throw new BusinessException("请将子项删除后再删除父项");
        }
        getBaseMapper().deleteById(id);
    }

    public void myPatch(AreaBO1 areaBO) {

        Area updating = BeanCopyUtils.copyBean(areaBO,Area.class);

        getBaseMapper().updateById(updating);

    }
}
