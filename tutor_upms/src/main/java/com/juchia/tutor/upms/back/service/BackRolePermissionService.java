package com.juchia.tutor.upms.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.upms.back.entity.dto.PermissionDTO;
import com.juchia.tutor.upms.common.entity.po.Permission;
import com.juchia.tutor.upms.common.entity.po.RolePermission;
import com.juchia.tutor.upms.common.mapper.RolePermissionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
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
public class BackRolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {

    @Autowired
    BackPermissionService backPermissionService;


    public List<PermissionDTO> listPermissionsByRoleId(Long roleId) {

        RolePermission condition = new RolePermission();
        condition.setRoleId(roleId);

        List<RolePermission> rolePermissions = list(new QueryWrapper<>(condition));

        if(rolePermissions.size()==0){
            return Collections.emptyList();
        }

        List<Long> permissionIds = rolePermissions.stream().map(rolePermission -> {
            return rolePermission.getPermissionId();
        }).collect(Collectors.toList());

        Collection<Permission> permissions = backPermissionService.listByIds(permissionIds);

        List<PermissionDTO> permissionDTOs = permissions.stream().map(permission -> {
            PermissionDTO permissionDTO = new PermissionDTO();
            BeanUtils.copyProperties(permission,permissionDTO);
            return permissionDTO;
        }).collect(Collectors.toList());

        return permissionDTOs;
    }

    public void saveRolePermissionsByRoleId(Long roleId, List<Long> permissionIds) {

        List<RolePermission> rolePermissions = permissionIds.stream().map(permissionId->{
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            return rolePermission;
        }).collect(Collectors.toList());

        //先删除原来的
        RolePermission condition = new RolePermission();
        condition.setRoleId(roleId);
        remove(new QueryWrapper<>(condition));

        //保存新的
        saveBatch(rolePermissions);
    }


    public void removeByRoleId(Long roleId) {
        RolePermission condition = new RolePermission();
        condition.setRoleId(roleId);
        remove(new QueryWrapper<>(condition));
    }


    public void removeByRoleIds(List<Long> roleIds) {
        if(roleIds.size()==0){
            return;
            //throw new BusinessException("删除列表为空");
        }
        QueryWrapper<RolePermission> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.in("role_id",roleIds);
        remove(deleteWrapper);
    }


    public void removeByPermissionId(Long permissionId) {
        RolePermission condition = new RolePermission();
        condition.setPermissionId(permissionId);
        remove(new QueryWrapper<>(condition));
    }


    public void removeByPermissionIds(List<Long> ids) {
        if(ids.size()==0){
            return;
            //throw new BusinessException("删除列表为空");
        }
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper();
        queryWrapper.in("permission_id",ids);
        remove(queryWrapper); //列表为空remove方法拼接sql失败,会抛异常
    }


    public List<PermissionDTO> listPermissionsByRoleIds(List<Long> roleIds) {
        if(roleIds.size()==0){
            return  Collections.emptyList();
        }
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper();
        queryWrapper.in("role_id",roleIds);

        List<RolePermission> rolePermissions = list(queryWrapper);

        List<Long> permissionIds = rolePermissions.stream().map(rolePermission -> {
          return rolePermission.getPermissionId();
        }).collect(Collectors.toList());

        Collection<Permission> permissions = backPermissionService.listByIds(permissionIds);

        List<PermissionDTO> permissionDTOs = permissions.stream().map(permission -> {
            PermissionDTO permissionDTO = new PermissionDTO();
            BeanUtils.copyProperties(permission,permissionDTO);
            return permissionDTO;
        }).collect(Collectors.toList());

        return permissionDTOs;
    }
}
