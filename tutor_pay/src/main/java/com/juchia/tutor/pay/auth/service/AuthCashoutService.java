package com.juchia.tutor.pay.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.pay.auth.entity.query.CashoutQuery1;
import com.juchia.tutor.pay.common.entity.po.Cashout;
import com.juchia.tutor.pay.common.mapper.CashoutMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthCashoutService extends ServiceImpl<CashoutMapper, Cashout> {
    public MyPage<Cashout> myPage(Long id, PageQuery pageQuery, CashoutQuery1 cashoutQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Cashout> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Cashout> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        if(cashoutQuery.getState()!=null){
            queryWrapper.eq("state",cashoutQuery.getState());
        }

//        3调用Mapper
        IPage<Cashout> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<Cashout> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }
}
