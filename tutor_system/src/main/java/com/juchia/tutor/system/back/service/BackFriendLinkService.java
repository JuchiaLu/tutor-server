package com.juchia.tutor.system.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.system.back.entity.bo.FriendLinkBO1;
import com.juchia.tutor.system.common.entity.po.FriendLink;
import com.juchia.tutor.system.common.mapper.FriendLinkMapper;
import com.juchia.tutor.system.front.entity.query.FriendLinkQuery1;
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
public class BackFriendLinkService extends ServiceImpl<FriendLinkMapper, FriendLink> {


    public MyPage<FriendLink> myPage(PageQuery pageQuery, FriendLinkQuery1 query) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<FriendLink> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<FriendLink> queryWrapper = new QueryWrapper<>();

        //      3调用Mapper
        IPage<FriendLink> iPage = getBaseMapper().selectPage(page, queryWrapper);


//        将mybatis plus 分页对象转换成我们的
        MyPage<FriendLink> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }


    public FriendLink mySave(FriendLinkBO1 bo) {

        FriendLink po = BeanCopyUtils.copyBean(bo, FriendLink.class);

        getBaseMapper().insert(po);

        return po;
    }

    public void myRemoveById(Long id) {
        getBaseMapper().deleteById(id);
    }

    public void myPatch(FriendLinkBO1 bo) {

        FriendLink updating = BeanCopyUtils.copyBean(bo,FriendLink.class);

        getBaseMapper().updateById(updating);

    }
}
