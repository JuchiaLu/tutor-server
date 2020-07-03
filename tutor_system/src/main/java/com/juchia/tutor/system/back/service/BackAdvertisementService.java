package com.juchia.tutor.system.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.system.back.entity.bo.AdvertisementBO1;
import com.juchia.tutor.system.back.entity.query.AdvertisementQuery1;
import com.juchia.tutor.system.common.entity.po.Advertisement;
import com.juchia.tutor.system.common.mapper.AdvertisementMapper;
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
public class BackAdvertisementService extends ServiceImpl<AdvertisementMapper, Advertisement> {


    public MyPage<Advertisement> myPage(PageQuery pageQuery, AdvertisementQuery1 query) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Advertisement> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Advertisement> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(query.getName())){
            queryWrapper.like("name",query.getName());
        }

        //      3调用Mapper
        IPage<Advertisement> iPage = getBaseMapper().selectPage(page, queryWrapper);



//        将mybatis plus 分页对象转换成我们的
        MyPage<Advertisement> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }


    public Advertisement mySave(AdvertisementBO1 bo) {

        Advertisement po = BeanCopyUtils.copyBean(bo, Advertisement.class);

        getBaseMapper().insert(po);

        return po;
    }

    public void myRemoveById(Long id) {
        getBaseMapper().deleteById(id);
    }

    public void myPatch(AdvertisementBO1 bo) {

        Advertisement updating = BeanCopyUtils.copyBean(bo,Advertisement.class);

        getBaseMapper().updateById(updating);

    }
}
