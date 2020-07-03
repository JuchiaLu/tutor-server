package com.juchia.tutor.system.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.system.common.entity.po.Notice;
import com.juchia.tutor.system.common.mapper.NoticeMapper;
import com.juchia.tutor.system.front.entity.query.NoticeQuery1;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
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
public class NoticeService extends ServiceImpl<NoticeMapper, Notice>{

    public MyPage<Notice> myPage(PageQuery pageQuery, NoticeQuery1 noticeQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Notice> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(noticeQuery.getSort())){
            if(StringUtils.equals("createTime",noticeQuery.getSort())){
                queryWrapper.orderBy(true,StringUtils.equals("asc",noticeQuery.getOrder()),"create_time");
            } else if(StringUtils.equals("weight",noticeQuery.getSort())){
                queryWrapper.orderBy(true,StringUtils.equals("asc",noticeQuery.getOrder()),"createTime");
            }
        }

//        3调用Mapper
        IPage<Notice> needDTOIPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<Notice> myPage = BeanCopyUtils.copyBean(needDTOIPage, MyPage.class);
        myPage.setPages(needDTOIPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }
}
