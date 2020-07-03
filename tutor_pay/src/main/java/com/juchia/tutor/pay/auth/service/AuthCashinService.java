package com.juchia.tutor.pay.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.pay.auth.entity.query.CashinQuery;
import com.juchia.tutor.pay.common.entity.po.Cashin;
import com.juchia.tutor.pay.common.mapper.CashinMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthCashinService extends ServiceImpl<CashinMapper,Cashin> {
    public MyPage<Cashin> myPage(Long id, PageQuery pageQuery, CashinQuery cashinQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Cashin> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Cashin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);

//        3调用Mapper
        IPage<Cashin> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<Cashin> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }
}
