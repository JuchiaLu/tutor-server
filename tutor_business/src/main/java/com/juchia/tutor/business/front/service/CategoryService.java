package com.juchia.tutor.business.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.common.entity.po.Category;
import com.juchia.tutor.business.common.mapper.CategoryMapper;
import com.juchia.tutor.business.front.entity.query.CategoryQuery1;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {
    public List<Category> Mylist(CategoryQuery1 categoryQuery) {
        //        areaQuery 查询条件请根据业务需求写

        return getBaseMapper().selectList(new QueryWrapper<>());
    }

    public List<Category> listByParentId(Long parentId) {
        Category condition = new Category();
        condition.setParentId(parentId);
        return getBaseMapper().selectList(new QueryWrapper<>(condition));
    }
}
