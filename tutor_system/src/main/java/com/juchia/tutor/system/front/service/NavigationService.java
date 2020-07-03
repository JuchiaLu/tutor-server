package com.juchia.tutor.system.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.system.common.entity.po.Navigation;
import com.juchia.tutor.system.common.mapper.NavigationMapper;
import com.juchia.tutor.system.front.entity.query.NavigationQuery1;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Service
public class NavigationService extends ServiceImpl<NavigationMapper, Navigation> {

    public List<Navigation> myList(NavigationQuery1 navigationQuery) {
        QueryWrapper<Navigation> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }
}
