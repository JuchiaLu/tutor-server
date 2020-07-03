package com.juchia.tutor.upms.feign.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.api.upms.bo.FeignUserBO1;
import com.juchia.tutor.api.upms.vo.FeignUserVO;
import com.juchia.tutor.upms.auth.entity.dto.UserDTO1;
import com.juchia.tutor.upms.common.entity.po.*;
import com.juchia.tutor.upms.common.exception.BusinessException;
import com.juchia.tutor.upms.common.mapper.*;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FeignUserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    public UserDTO1 getUserInfoByUsername(String username) {

        //        找出用户,
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.or();
        queryWrapper.eq("email",username);
        queryWrapper.or();
        queryWrapper.eq("phone",username);
        User has = getBaseMapper().selectOne(queryWrapper);
        if(has==null){
            log.info("用户不存在: username={}",username);
            return null; // TODO 远程调用抛异常还是返回 统一结果？
        }

//        查中间表得到全部角色id
        QueryWrapper<UserRole> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id",has.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper1);
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


        UserDTO1 userDTO1 = BeanCopyUtils.copyBean(has, UserDTO1.class);
        userDTO1.setRoles(roles);
        userDTO1.setPermissions(permissions);

        return userDTO1;
    }

    public User saveUser(FeignUserBO1 feignUserBO) {

//       重复校验
        User condition = new User();
        condition.setUsername(feignUserBO.getUsername());
        int count = count(new QueryWrapper<>(condition));
        if (count>0) {
            log.info("用户名已存在:{}",feignUserBO.getUsername());
            throw new BusinessException("用户名已存在!");
        }

//      bo 转 po 并字段处理
        User saving = BeanCopyUtils.copyBean(feignUserBO,User.class);
        saving.setId(null); //TODO 在service层处理还是controller层?

        getBaseMapper().insert(saving);
        return saving;
    }

//    public FeignUserDTO getUserByUsername(String username) throws ResourceNotFondException {
//        User condition = new User();
//        condition.setUsername(username);
//        User has = getOne(new QueryWrapper<>(condition),false);
//        if(has==null){
//            log.info("资源不存在: username={}",username);
//            throw new ResourceNotFondException("资源不存在: username="+username);
//        }
//        FeignUserDTO userDTO = new FeignUserDTO();
//        BeanUtils.copyProperties(has,userDTO);
//        return userDTO;
//    }

    public FeignUserVO updateUser(FeignUserBO1 user) {
        User has = getById(user.getId());
        if(has==null){
            log.info("用户不存在:{}",user.getUsername());
            throw new BusinessException("用户不存在!");
        }

        User updating = new User();
        BeanUtils.copyProperties(user,updating);

        updating.setCreateTime(has.getCreateTime()); //不允许更新创建时间
        updating.setUpdateTime(LocalDateTime.now()); //自动生成更新时间
        updating.setUsername(has.getUsername()); //不允许修改用户名
        if(StringUtils.isNotBlank(user.getPassword())){
            updating.setPassword("新密码"+user.getPassword()); // TODO 用加密器加密密码
        }else{
            updating.setPassword(has.getPassword());
        }

        updateById(updating);

        FeignUserVO userDTO = new FeignUserVO();
        BeanUtils.copyProperties(updating,userDTO);

        return userDTO;
    }

    public FeignUserVO patchUser(FeignUserBO1 user) {

        User updating = new User();
        BeanUtils.copyProperties(user,updating);

        updating.setCreateTime(null); //不允许修改创建时间
        updating.setUpdateTime(LocalDateTime.now()); //自动生成更新时间
        updating.setUsername(null); //不允许修改用户名
        if(StringUtils.isNotBlank(user.getPassword())){
            updating.setPassword("新密码"+user.getPassword()); // TODO 用加密器加密密码
        }else{
            updating.setPassword(null); //不用修改密码
        }

        if(!updateById(updating)){
            log.info("用户不存在:{}",user.getUsername());
            throw new BusinessException("用户不存在!");
        }

        FeignUserVO userDTO = new FeignUserVO();
        BeanUtils.copyProperties(updating,userDTO);

        return userDTO;

    }

    public UserDTO1 getUserInfoById(Long id) {
        //        找出用户,
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        User has = getBaseMapper().selectOne(queryWrapper);
        if(has==null){
            log.info("用户不存在: username={}",id);
            return null; // TODO 远程调用抛异常还是返回 统一结果？
        }

//        查中间表得到全部角色id
        QueryWrapper<UserRole> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id",has.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper1);
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


        UserDTO1 userDTO1 = BeanCopyUtils.copyBean(has, UserDTO1.class);
        userDTO1.setRoles(roles);
        userDTO1.setPermissions(permissions);

        return userDTO1;
    }
}
