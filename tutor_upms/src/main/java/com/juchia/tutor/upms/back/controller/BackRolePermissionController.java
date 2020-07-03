package com.juchia.tutor.upms.back.controller;


import com.juchia.tutor.upms.back.entity.dto.PermissionDTO;
import com.juchia.tutor.upms.back.service.BackRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Validated
@RestController
@Slf4j
public class BackRolePermissionController {

    @Autowired
    BackRolePermissionService backRolePermissionService;

    /**
     * 根据角色id查询角色的权限列表
     */
    @PreAuthorize("hasAuthority('upms:rolePermission:select')")
    @GetMapping("back/roles/{roleId}/permissions")
    List<PermissionDTO> listPermissionsByRoleId(@PathVariable("roleId") Long roleId){
        List<PermissionDTO> permissionDTOs = backRolePermissionService.listPermissionsByRoleId(roleId);
        return permissionDTOs;
    }


    /**
     * 根据角色id和权限id列表为角色添加权限
     */
    @PreAuthorize("hasAuthority('upms:rolePermission:update')")
    @PutMapping("back/roles/{roleId}/permissions")
    void saveRolePermissionsByRoleId(@PathVariable("roleId") Long roleId,@RequestBody List<Long> permissionIds){
        backRolePermissionService.saveRolePermissionsByRoleId(roleId,permissionIds);
    }



}

