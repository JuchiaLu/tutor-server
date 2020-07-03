package com.juchia.tutor.system.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.system.common.entity.po.Route;
import com.juchia.tutor.system.common.mapper.RouteMapper;
import com.juchia.tutor.system.front.entity.query.RouteQuery1;
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
public class RouteService extends ServiceImpl<RouteMapper, Route> {

    public List<Route> myList(RouteQuery1 routeQuery) {
        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }
}
