package com.juchia.tutor.business.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.back.entity.bo.DictTypeBO1;
import com.juchia.tutor.business.back.entity.query.DictTypeQuery1;
import com.juchia.tutor.business.common.entity.po.DictType;
import com.juchia.tutor.business.common.mapper.DictTypeMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

@Service
public class BackDictTypeService extends ServiceImpl<DictTypeMapper, DictType> {



    public DictType mySave(DictTypeBO1 dictTypeBO) {
        DictType dictType = BeanCopyUtils.copyBean(dictTypeBO, DictType.class);
        getBaseMapper().insert(dictType);
        return dictType;
    }

    public DictType myUpdate(DictTypeBO1 dictTypeBO) {
        DictType dictType = BeanCopyUtils.copyBean(dictTypeBO, DictType.class);
        getBaseMapper().updateById(dictType);
        return dictType;
    }

    public MyPage<DictType> myPage(PageQuery pageQuery, DictTypeQuery1 dictTypeQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<DictType> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<DictType> queryWrapper = new QueryWrapper<>();

        //      3调用Mapper
        IPage<DictType> iPage = getBaseMapper().selectPage(page, queryWrapper);


//        将mybatis plus 分页对象转换成我们的
        MyPage<DictType> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }
}
