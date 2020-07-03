package com.juchia.tutor.upms.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juchia.tutor.upms.back.entity.bo.RouteBO1;
import com.juchia.tutor.upms.back.entity.dto.RouteDTO;
import com.juchia.tutor.upms.back.entity.query.RouteQuery1;
import com.juchia.tutor.upms.back.service.BackRouteService;
import com.juchia.tutor.upms.common.entity.po.Route;
import com.juchia.tutor.upms.common.exception.ResourceNotFondException;
import com.juchia.tutor.upms.common.validate.group.Insert;
import com.juchia.tutor.upms.common.validate.group.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("back/routes")
public class BackRouteController {

    @Autowired
    BackRouteService backRouteService;

    /**
     * 增加单个路由
     * TODO 返回新增的id 还是 返回整个Route?
     * @param
     * @param
     * @return 新添加的路由
     */
    @PreAuthorize("hasAuthority('upms:route:insert')")
    @PostMapping
    public RouteDTO saveRoute(@Validated(Insert.class) @RequestBody RouteBO1 route){
        route.setId(null);
        return backRouteService.saveRoute(route);
    }


    /**
     * 根据Id删除单个路由
     * TODO 删除是否需要把删除成功的路由返回去，各大公司的都没有返回，原因是什么?
     */
    @PreAuthorize("hasAuthority('upms:route:delete')")
    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeRouteById(@PathVariable("id") Long id){
        backRouteService.removeRouteById(id);
    }

    /**
     * 根据Ids删除多个路由
     */
    @PreAuthorize("hasAuthority('upms:route:delete')")
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeRoutesByIds( @RequestBody List<Long> ids){
        backRouteService.removeRoutesByIds(ids);
    }

    /**
     * 覆盖修改单个路由
     */
    @PreAuthorize("hasAuthority('upms:route:update')")
    @PutMapping
    public RouteDTO updateRoute(@Validated(Update.class) @RequestBody RouteBO1 route){
        return backRouteService.updateRoute(route);
    }

    @PreAuthorize("hasAuthority('upms:route:update')")
    @PatchMapping
    public RouteDTO patchRoute(@Validated(Update.class) @RequestBody RouteBO1 route){
        return backRouteService.patchRoute(route);
    }

    /**
     * 根据id查询单个路由
     * @return 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:route:select')")
    @GetMapping("{id}")
    public RouteDTO getRouteById( @PathVariable("id") Long  id) throws ResourceNotFondException {
        return backRouteService.getRouteById(id);
    }


    /**
     * 根据路由名查询单个路由
     * @param name
     * @return 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:route:select')")
    @GetMapping("name/{name}")
    public RouteDTO getRouteByName(@PathVariable("name") String name) throws ResourceNotFondException {
        return backRouteService.getRouteByName(name);
    }

    /**
     * 多条件查询+分页+排序
     * TODO 直接使用 mybatis plus 提供的Page还是自定义一个PageVO？
     * TODO 不能直接返回数据层的 Route 要返回 RouteView 去掉密码等字段，防止字段暴露
     * 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:route:select')")
    @GetMapping("page")
    public IPage<RouteDTO> pageRoutes(Page<Route> pageQuery, RouteQuery1 routeQuery){
        return backRouteService.pageRoutes(pageQuery, routeQuery);
    }

    @PreAuthorize("hasAuthority('upms:route:select')")
    @GetMapping
    public List<RouteDTO> listRoutes(RouteQuery1 routeQuery){
        return backRouteService.listRoutes(routeQuery);
    }


    /**
     * 以下开始测试
     */


    /**
     * 根据父id获取子路由列表
     * 没有返回空列表不抛异常
     * @param parentId
     * @return
     */
    @PreAuthorize("hasAuthority('upms:route:select')")
    @GetMapping("parentId/{parentId}")
    List<RouteDTO> listRoutesByParentId(@PathVariable("parentId") Long parentId){
        return backRouteService.listRoutesByParentId(parentId);
    }



}

