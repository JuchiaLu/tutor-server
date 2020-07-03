package com.juchia.tutor.upms.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.upms.back.entity.bo.RouteBO1;
import com.juchia.tutor.upms.back.entity.dto.RouteDTO;
import com.juchia.tutor.upms.back.entity.query.RouteQuery1;
import com.juchia.tutor.upms.common.entity.po.Route;
import com.juchia.tutor.upms.common.exception.BusinessException;
import com.juchia.tutor.upms.common.exception.ResourceNotFondException;
import com.juchia.tutor.upms.common.mapper.RouteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class BackRouteService extends ServiceImpl<RouteMapper, Route>  {


    public RouteDTO saveRoute(RouteBO1 route) {
        Route condition = new Route();
        condition.setName(route.getName());

//        允许名字相同
//        int count = count(new QueryWrapper<>(condition));
//        if (count>0) {
//            log.info("路由名已存在:{}",route.getName());
//            throw new BusinessException("路由名已存在!");
//        }

        Route saving = new Route();
        BeanUtils.copyProperties(route,saving);
//        字段处理
        LocalDateTime now = LocalDateTime.now();
        saving.setCreateTime(now);
        saving.setUpdateTime(now);
//        saving.setRouteId(null); TODO 在service层处理还是controller层?

        save(saving);

        RouteDTO routeDTO = new RouteDTO();
        BeanUtils.copyProperties(saving,routeDTO);

        return routeDTO;
    }


    public void removeRouteById(Long id) {
//         删除自己
        removeById(id);

//          获取子路由
        List<RouteDTO> children = listRoutesByParentId(id);

        if(children.size()==0){
            return;
        }

//        删除子路由
        List<Long> ids = children.stream().map(route -> {
            return route.getId();
        }).collect(Collectors.toList());
        removeByIds(ids);

    }

    //TODO 未实现

    public void removeRoutesByIds(List<Long> ids) {
        if(ids.size()==0){
            return;
        }
        removeByIds(ids);
    }


    public RouteDTO updateRoute(RouteBO1 route) {
        Route has = getById(route.getId());
        if(has==null){
            log.info("路由不存在:{}",route.getName());
            throw new BusinessException("路由不存在!");
        }

        Route updating = new Route();
        BeanUtils.copyProperties(route,updating);

        updating.setCreateTime(has.getCreateTime()); //不允许更新创建时间
        updating.setUpdateTime(LocalDateTime.now()); //自动生成更新时间

        updateById(updating);
        RouteDTO routeDTO = new RouteDTO();
        BeanUtils.copyProperties(updating,routeDTO);

        return routeDTO;
    }


    public RouteDTO patchRoute(RouteBO1 route) {

        Route updating = new Route();
        BeanUtils.copyProperties(route,updating);

        updating.setCreateTime(null); //不允许更新创建时间
        updating.setUpdateTime(LocalDateTime.now()); //自动生成更新时间

        if(!updateById(updating)){
            log.info("路由不存在:{}",route.getName());
            throw new BusinessException("路由不存在!");
        }
        RouteDTO routeDTO = new RouteDTO();
        BeanUtils.copyProperties(updating,routeDTO);

        return routeDTO;
    }


    public RouteDTO getRouteById(Long id) throws ResourceNotFondException {
        Route route = getById(id);
        if(route==null){
            log.info("资源不存在: id={}",id);
            throw new ResourceNotFondException("资源不存在: id="+id);
        }
        RouteDTO routeDTO = new RouteDTO();
        BeanUtils.copyProperties(route,routeDTO);
        return routeDTO;
    }


    public RouteDTO getRouteByName(String name) throws ResourceNotFondException {
        Route condition = new Route();
        condition.setName(name);
        Route has = getOne(new QueryWrapper<>(condition),false);
        if(has==null){
            log.info("资源不存在: name={}",name);
            throw new ResourceNotFondException("资源不存在: name="+name);
        }
        RouteDTO routeDTO = new RouteDTO();
        BeanUtils.copyProperties(has,routeDTO);
        return routeDTO;
    }


    public List<RouteDTO> listRoutes(RouteQuery1 routeQuery) {
        List<Route> routes = list(routeQuery.buildQueryWrapper());
        List<RouteDTO> routeDTOs = routes.stream().map(route -> {
            RouteDTO routeDTO = new RouteDTO();
            BeanUtils.copyProperties(route,routeDTO);
            return routeDTO;
        }).collect(Collectors.toList());
        return routeDTOs;
    }



    public IPage<RouteDTO> pageRoutes(Page<Route> pageQuery, RouteQuery1 routeQuery) {
        IPage<Route> page = page(pageQuery, routeQuery.buildQueryWrapper());

        List<RouteDTO> routeDTOs = page.getRecords().stream().map(route -> {
            RouteDTO routeDTO = new RouteDTO();
            BeanUtils.copyProperties(route,routeDTO);
            return routeDTO;
        }).collect(Collectors.toList());

        IPage<RouteDTO> routeDTOIPage = new Page<>();
        BeanUtils.copyProperties(page,routeDTOIPage);
        routeDTOIPage.setRecords(routeDTOs);

        return routeDTOIPage;

    }

    //    以下开始测试

    public List<RouteDTO> listRoutesByParentId(Long parentId) {
        Route condition = new Route();
        condition.setParentId(parentId);
        List<Route> routes = list(new QueryWrapper<>(condition));

        List<RouteDTO> routeDTOs = routes.stream().map(route -> {
            RouteDTO routeDTO = new RouteDTO();
            BeanUtils.copyProperties(route,routeDTO);
            return routeDTO;
        }).collect(Collectors.toList());

        return routeDTOs;
    }


}
