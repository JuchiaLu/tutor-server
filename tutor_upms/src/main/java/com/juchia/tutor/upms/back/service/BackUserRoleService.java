package com.juchia.tutor.upms.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.upms.back.entity.dto.RoleDTO;
import com.juchia.tutor.upms.common.entity.po.Role;
import com.juchia.tutor.upms.common.entity.po.UserRole;
import com.juchia.tutor.upms.common.mapper.UserRoleMapper;
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
public class BackUserRoleService extends ServiceImpl<UserRoleMapper, UserRole>  {

    @Autowired
    BackRoleService backRoleService;


    public List<RoleDTO> listRolesByUserId(Long userId) {

        UserRole condition = new UserRole();
        condition.setUserId(userId);

        List<UserRole> userRoles = list(new QueryWrapper<>(condition));

        if(userRoles.size()==0){
            return Collections.emptyList();
        }

        List<Long> roleIds = userRoles.stream().map(userRole -> {
            return userRole.getRoleId();
        }).collect(Collectors.toList());

        Collection<Role> roles = backRoleService.listByIds(roleIds);

        List<RoleDTO> roleDTOs = roles.stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(role,roleDTO);
            return roleDTO;
        }).collect(Collectors.toList());

        return roleDTOs;
    }


    public void saveUserRolesByUserId(Long userId, List<Long> roleIds) {

        List<UserRole> userRoles = roleIds.stream().map(roleId->{
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRole;
        }).collect(Collectors.toList());

        //先删除原来的
        UserRole condition = new UserRole();
        condition.setUserId(userId);
        remove(new QueryWrapper<>(condition));

        //重新插入
        saveBatch(userRoles);

        return;
    }


    public void removeByUserId(Long userId) {
        UserRole condition = new UserRole();
        condition.setUserId(userId);
        remove(new QueryWrapper<>(condition));
        return;
    }


    public void removeByUserIds(List<Long> userIds) {
        if(userIds.size()==0){
            return;
        }
        QueryWrapper<UserRole> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.in("user_id",userIds); //TODO Wrapper直接操作数据库字段违反ORM
        remove(deleteWrapper);
    }
}
