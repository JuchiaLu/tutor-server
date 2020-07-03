package com.juchia.tutor.upms.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectBatchByIds;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.upms.auth.entity.bo.UserBO2;
import com.juchia.tutor.upms.auth.entity.dto.UserDTO1;
import com.juchia.tutor.upms.back.entity.dto.UserDTO;
import com.juchia.tutor.upms.common.entity.po.*;
import com.juchia.tutor.upms.common.exception.BusinessException;
import com.juchia.tutor.upms.common.mapper.*;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 * 调用DAO 查出来的的是PO 将多个PO封装成BO 也可以将单个PO转换成DTO
 * 为了展示方便，在VO就是男、女，而在DTO当中为了实现方便传输的是1或者2这样的代码
 * 要去掉字段时也可以在DTO中去掉
 * @author juchia
 * @since 2019-08-23
 */
@Service
@Slf4j
public class AuthUserService extends ServiceImpl<UserMapper, User> {


    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDTO1 getUserInfoById(Long id){
//        找出用户,
        User user = getBaseMapper().selectById(id);
        if(user == null){
            log.info("用户不存在: id={}",id);
            throw new BusinessException("用户不存在");
        }
//        查中间表得到全部角色id
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

//      查中间表得到全部权限id
        List<Long> permissionIds = new ArrayList<>(0);
        QueryWrapper<RolePermission> queryWrapper2 = new QueryWrapper<>();
        if(roleIds.size()!=0){
            queryWrapper2.in("role_id",roleIds);
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(queryWrapper2);
            permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        }


//      查出全部角色
        List<Role> roles = new ArrayList<>(0);
        if(roleIds.size()!=0){
            roles = roleMapper.selectBatchIds(roleIds);
        }


//      查出全部权限
        List<Permission> permissions = new ArrayList<>(0);
        if(permissionIds.size()!=0){
            permissions = permissionMapper.selectBatchIds(permissionIds);
        }


//      构建DTO
        UserDTO1 userDTO1 = BeanCopyUtils.copyBean(user, UserDTO1.class);
        userDTO1.setRoles(roles);
        userDTO1.setPermissions(permissions);

        return userDTO1;
    }

    public void changePassword(Long id, UserBO2 userBO) {
        //        找出用户,
        User user = getBaseMapper().selectById(id);
        if(user == null){
            log.info("用户不存在: id={}",id);
            throw new BusinessException("用户不存在");
        }
        user.setPassword(passwordEncoder.encode(userBO.getPassword()));
        getBaseMapper().updateById(user);
    }
}
