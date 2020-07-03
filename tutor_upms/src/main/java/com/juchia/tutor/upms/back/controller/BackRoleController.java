package com.juchia.tutor.upms.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juchia.tutor.upms.back.entity.bo.RoleBO1;
import com.juchia.tutor.upms.back.entity.dto.RoleDTO;
import com.juchia.tutor.upms.back.entity.query.RoleQuery1;
import com.juchia.tutor.upms.back.service.BackRoleService;
import com.juchia.tutor.upms.common.entity.po.Role;
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
@RequestMapping("back/roles")
public class BackRoleController {

    @Autowired
    BackRoleService backRoleService;

    /**
     * 增加单个角色
     * TODO 返回新增的id 还是 返回整个Role?
     * @param
     * @param
     * @return 新添加的角色
     */
    @PreAuthorize("hasAuthority('upms:role:insert')")
    @PostMapping
    public RoleDTO saveRole(@Validated(Insert.class) @RequestBody RoleBO1 role){
        role.setId(null);
        return backRoleService.saveRole(role);
    }


    /**
     * 根据Id删除单个角色
     * TODO 删除是否需要把删除成功的角色返回去，各大公司的都没有返回，原因是什么?
     */
    @PreAuthorize("hasAuthority('upms:role:delete')")
    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeRoleById(@PathVariable("id") Long id){
        backRoleService.removeRoleById(id);
    }

    /**
     * 根据Ids删除多个角色
     */
    @PreAuthorize("hasAuthority('upms:role:delete')")
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeRolesByIds( @RequestBody List<Long> ids){
        backRoleService.removeRolesByIds(ids);
    }

    /**
     * 覆盖修改单个角色
     */
    @PreAuthorize("hasAuthority('upms:role:update')")
    @PutMapping
    public RoleDTO updateRole(@Validated(Update.class) @RequestBody RoleBO1 role){
        return backRoleService.updateRole(role);
    }


    @PreAuthorize("hasAuthority('upms:role:update')")
    @PatchMapping
    public RoleDTO patchRole(@Validated(Update.class) @RequestBody RoleBO1 role){
        return backRoleService.patchRole(role);
    }


    /**
     * 根据id查询单个角色
     * @return 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:role:select')")
    @GetMapping("{id}")
    public RoleDTO getRoleById( @PathVariable("id") Long  id) throws ResourceNotFondException {
        return backRoleService.getRoleById(id);
    }


    /**
     * 根据角色名查询单个角色
     * @param name
     * @return 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:role:select')")
    @GetMapping("name/{name}")
    public RoleDTO getRoleByName(@PathVariable("name") String name) throws ResourceNotFondException {
        return backRoleService.getRoleByName(name);
    }

    /**
     * 多条件查询+分页+排序
     * TODO 直接使用 mybatis plus 提供的Page还是自定义一个PageVO？
     * TODO 不能直接返回数据层的 Role 要返回 RoleView 去掉密码等字段，防止字段暴露
     * 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:role:select')")
    @GetMapping("page")
    public IPage<RoleDTO> pageRoles(Page<Role> pageQuery, RoleQuery1 roleQuery){
        return backRoleService.pageRoles(pageQuery, roleQuery);
    }

    @PreAuthorize("hasAuthority('upms:role:select')")
    @GetMapping
    public List<RoleDTO> listRoles(RoleQuery1 roleQuery){
        return backRoleService.listRoles(roleQuery);
    }

}

