package com.juchia.tutor.upms.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.upms.front.entity.query.RouteQuery1;
import com.juchia.tutor.upms.common.entity.po.Route;
import com.juchia.tutor.upms.common.mapper.RouteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author juchia
 * @since 2019-08-23
 */
@Service
@Slf4j
public class RouteService extends ServiceImpl<RouteMapper, Route>{

    public List<Route> listRoutes(RouteQuery1 routeQuery) {
        return getBaseMapper().selectList(new QueryWrapper<>());
    }
}
