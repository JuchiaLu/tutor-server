package com.juchia.tutor.business.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.back.entity.query.AppointQuery1;
import com.juchia.tutor.business.common.entity.po.Appoint;
import com.juchia.tutor.business.common.mapper.AppointMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BackAppointService extends ServiceImpl<AppointMapper, Appoint> {

    public MyPage<Appoint> myPage(PageQuery pageQuery, AppointQuery1 appointQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Appoint> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Appoint> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(appointQuery.getNickname())){
            queryWrapper.like("nickname",appointQuery.getNickname());
        }
        if(appointQuery.getState()!=null){
            queryWrapper.eq("state",appointQuery.getState());
        }


//        3调用Mapper
        IPage<Appoint> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<Appoint> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }
}
