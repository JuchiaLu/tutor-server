package com.juchia.tutor.business.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.back.entity.bo.NeedBO1;
import com.juchia.tutor.business.back.entity.query.NeedQuery1;
import com.juchia.tutor.business.common.entity.dto.NeedDTO;
import com.juchia.tutor.business.common.entity.po.Need;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.NeedMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BackNeedService extends ServiceImpl<NeedMapper, Need> {

    public MyPage<NeedDTO> pageDTO(PageQuery pageQuery, NeedQuery1 needQuery) {
//      1 将我们的分页对象转换成mybatis plus 的
        Page<NeedDTO> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<NeedDTO> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(needQuery.getNickname())){
            queryWrapper.like("nickname",needQuery.getNickname());
        }
        if(needQuery.getState()!=null){
            queryWrapper.eq("state",needQuery.getState());
        }

//        3调用Mapper
        IPage<NeedDTO> needDTOIPage = getBaseMapper().selectPageDTO(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<NeedDTO> myPage = BeanCopyUtils.copyBean(needDTOIPage, MyPage.class);
        myPage.setPages(needDTOIPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }


    public void audit(Long id, NeedBO1 needBO) {
        Need meed  = getBaseMapper().selectById(id);
        if(meed==null){
            throw new BusinessException("id参数错误");
        }

        if(meed.getState().compareTo(1)!=0){ //待审核
            throw new BusinessException("状态错误");
        }

        if(needBO.getState()==null){
            throw new BusinessException("参数错误");
        }
        if(needBO.getState().compareTo(2)==0){ //审核失败
            meed.setState(2);
            if(StringUtils.isNotBlank(needBO.getReason())){
                meed.setReason(needBO.getReason());
            }
        }else if(needBO.getState().compareTo(3)==0){ //审核通过
            meed.setState(3);
        }else {
            throw new BusinessException("参数错误");
        }


        getBaseMapper().updateById(meed);
    }
}
