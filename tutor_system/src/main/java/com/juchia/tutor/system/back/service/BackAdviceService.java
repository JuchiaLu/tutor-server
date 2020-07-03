package com.juchia.tutor.system.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.system.back.entity.bo.AdviceBO1;
import com.juchia.tutor.system.back.entity.query.AdviceQuery1;
import com.juchia.tutor.system.common.entity.po.Advice;
import com.juchia.tutor.system.common.mapper.AdviceMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Service
public class BackAdviceService extends ServiceImpl<AdviceMapper, Advice> {


    public MyPage<Advice> myPage(PageQuery pageQuery, AdviceQuery1 query) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Advice> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Advice> queryWrapper = new QueryWrapper<>();

        //      3调用Mapper
        IPage<Advice> iPage = getBaseMapper().selectPage(page, queryWrapper);


//        将mybatis plus 分页对象转换成我们的
        MyPage<Advice> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }


    public Advice mySave(AdviceBO1 bo) {

        Advice po = BeanCopyUtils.copyBean(bo, Advice.class);

        getBaseMapper().insert(po);

        return po;
    }

    public void myRemoveById(Long id) {
        getBaseMapper().deleteById(id);
    }

    public void myPatch(AdviceBO1 bo) {

        Advice updating = BeanCopyUtils.copyBean(bo,Advice.class);

        getBaseMapper().updateById(updating);

    }
}
