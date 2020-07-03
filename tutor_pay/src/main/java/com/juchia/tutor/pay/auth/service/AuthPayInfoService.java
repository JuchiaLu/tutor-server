package com.juchia.tutor.pay.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.pay.auth.entity.query.PayInfoQuery1;
import com.juchia.tutor.pay.common.entity.po.PayInfo;
import com.juchia.tutor.pay.common.mapper.PayInfoMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthPayInfoService extends ServiceImpl<PayInfoMapper,PayInfo> {
    public MyPage<PayInfo> myPage(Long id, PageQuery pageQuery, PayInfoQuery1 payInfoQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<PayInfo> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<PayInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("to_id",id);
        if(payInfoQuery.getState()!=null){
            queryWrapper.eq("state",payInfoQuery.getState());
        }

//        3调用Mapper
        IPage<PayInfo> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<PayInfo> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }
}
