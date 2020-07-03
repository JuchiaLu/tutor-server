package com.juchia.tutor.business.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.back.entity.bo.CategoryBO1;
import com.juchia.tutor.business.back.entity.query.CategoryQuery1;
import com.juchia.tutor.business.common.entity.po.Category;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.CategoryMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackCategoryService extends ServiceImpl<CategoryMapper, Category> {
    public List<Category> Mylist(CategoryQuery1 categoryQuery) {
        //        areaQuery 查询条件请根据业务需求写

        return getBaseMapper().selectList(new QueryWrapper<>());
    }

    public List<Category> listByParentId(Long parentId) {
        Category condition = new Category();
        condition.setParentId(parentId);
        return getBaseMapper().selectList(new QueryWrapper<>(condition));
    }

    public Category mySave(CategoryBO1 categoryBO) {

        Category category = BeanCopyUtils.copyBean(categoryBO, Category.class);

        getBaseMapper().insert(category);

        return category;
    }

    public void myRemoveById(Long id) {
        Category condition = new Category();
        condition.setParentId(id);
        Integer count = getBaseMapper().selectCount(new QueryWrapper<>(condition));
        if(count.compareTo(0)!=0){
            throw new BusinessException("请将子项删除后再删除父项");
        }
        getBaseMapper().deleteById(id);
    }

    public void myPatch(CategoryBO1 categoryBO) {

        Category updating = BeanCopyUtils.copyBean(categoryBO,Category.class);

        getBaseMapper().updateById(updating);

    }
}
