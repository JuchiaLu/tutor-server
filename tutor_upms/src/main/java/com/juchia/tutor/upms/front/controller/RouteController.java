package com.juchia.tutor.upms.front.controller;

import com.juchia.tutor.upms.common.entity.po.Route;
import com.juchia.tutor.upms.front.entity.query.RouteQuery1;
import com.juchia.tutor.upms.front.entity.vo.RouteVO;
import com.juchia.tutor.upms.front.service.RouteService;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author juchia
 * @since 2019-08-23
 */
@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    RouteService frontRouteService;

    @GetMapping
    public List<RouteVO> listRoutes(RouteQuery1 routeQuery){
        List<Route> routes = frontRouteService.listRoutes(routeQuery);
        return BeanCopyUtils.copyList(routes,RouteVO.class);
    }

}

