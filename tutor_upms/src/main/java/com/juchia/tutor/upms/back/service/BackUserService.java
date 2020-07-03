package com.juchia.tutor.upms.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.upms.back.entity.bo.UserBO1;
import com.juchia.tutor.upms.back.entity.dto.PermissionDTO;
import com.juchia.tutor.upms.back.entity.dto.RoleDTO;
import com.juchia.tutor.upms.back.entity.dto.UserDTO;
import com.juchia.tutor.upms.back.entity.dto.UserInfo;
import com.juchia.tutor.upms.back.entity.query.UserQuery1;
import com.juchia.tutor.upms.common.entity.po.User;
import com.juchia.tutor.upms.common.exception.BusinessException;
import com.juchia.tutor.upms.common.exception.ResourceNotFondException;
import com.juchia.tutor.upms.common.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class BackUserService extends ServiceImpl<UserMapper, User> {


    @Autowired
    BackUserRoleService backUserRoleService;

    @Autowired
    BackRolePermissionService backRolePermissionService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserDTO saveUser(UserBO1 user) {

        // TODO 前台已经校验过, 越过浏览器访问的,出现的异常是否直接交给数据库处理?
        // TODO 然后捕获数据库的异常直接抛出去 或 为了数据库可移植性不要依赖数据库
        User condition = new User();
        condition.setUsername(user.getUsername());

        int count = count(new QueryWrapper<>(condition));
        if (count>0) {
            log.info("用户名已存在:{}",user.getUsername());
            throw new BusinessException("用户名已存在!");
        }

        User saving = new User();
        BeanUtils.copyProperties(user,saving);
//        字段处理
        LocalDateTime now = LocalDateTime.now();
        saving.setCreateTime(now);
        saving.setUpdateTime(now);
        saving.setPassword(passwordEncoder.encode(user.getPassword())); // TODO 用加密器加密密码
        saving.setFront(0); //不是前台用户

        save(saving);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(saving,userDTO);

        return userDTO;
    }


    public UserDTO updateUser(UserBO1 user) {

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

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(updating,userDTO);

        return userDTO;
    }


    public UserDTO getUserByUsername(String username) throws ResourceNotFondException {
        User condition = new User();
        condition.setUsername(username);
        User has = getOne(new QueryWrapper<>(condition),false);
        if(has==null){
            log.info("资源不存在: username={}",username);
            throw new ResourceNotFondException("资源不存在: username="+username);
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(has,userDTO);
        return userDTO;
    }


    public void removeUserById(Long id) {
        removeById(id);
        backUserRoleService.removeByUserId(id);
    }


    public void removeUsersByIds(List<Long> ids) {
        if(ids.size()==0){
            return;
        }
        removeByIds(ids);
        backUserRoleService.removeByUserIds(ids);
    }


    public IPage<UserDTO> pageUsers(Page<User> pageQuery, UserQuery1 userQuery) {
        IPage<User> page = page(pageQuery, userQuery.buildQueryWrapper());

        List<UserDTO> userDTOs = page.getRecords().stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            return userDTO;
        }).collect(Collectors.toList());

        IPage<UserDTO> userDTOIPage = new Page<>();
        BeanUtils.copyProperties(page,userDTOIPage);
        userDTOIPage.setRecords(userDTOs);

        return userDTOIPage;

    }


    public UserInfo getUserInfoByUsername(String username) throws ResourceNotFondException {
//        找出用户,
        UserDTO userDTO = getUserByUsername(username);
        if(userDTO == null){
            log.info("用户不存在: username={}",username);
            throw new ResourceNotFondException("用户不存在: username="+username);
        }
//        根据用户id找出角色
        List<RoleDTO> roles = backUserRoleService.listRolesByUserId(userDTO.getId());

        List<Long> roleIds = roles.stream().map(RoleDTO::getId).collect(Collectors.toList());

//        根据角色ids找出权限
        List<PermissionDTO> permissionDTOs = backRolePermissionService.listPermissionsByRoleIds(roleIds);

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userDTO,userInfo);

        userInfo.setRoles(roles);
        userInfo.setPermissions(permissionDTOs);
        return userInfo;
    }


    public UserDTO getUserById(Long id) throws ResourceNotFondException {
        User user = getById(id);
        if (user==null){
            log.info("资源不存在: id={}",id);
            throw new ResourceNotFondException("资源不存在: id="+id);
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }


    public List<UserDTO> listUsers(UserQuery1 userQuery) {
        List<User> users = list(userQuery.buildQueryWrapper());
        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            return userDTO;
        }).collect(Collectors.toList());

        return userDTOs;
    }


    public UserDTO patchUser(UserBO1 user) {
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

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(updating,userDTO);

        return userDTO;
    }


    public UserDTO getUserByUsernameOrPhoneOrEmail(String username,String phone,String email) throws ResourceNotFondException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.eq("username",username);
        }
        if(StringUtils.isNotBlank(phone)){
            queryWrapper.or().eq("phone",phone);
        }
        if(StringUtils.isNotBlank(email)){
            queryWrapper.or().eq("email",email);
        }
        //这里添加时要确保一个邮箱,一个手机号,只能绑定一个帐号,且帐号要有限制,不能和手机号或邮箱号相同
        //比如 不能用存数字,不能用特殊字符做用户名即可
        List<User> users = list(queryWrapper);; //如果查出多个,这里会抛异常,说明插入时检查有问题
        if(users.size()==0){
            log.info("资源不存在!");
            throw new ResourceNotFondException("资源不存在!");
        }
        if(users.size()!=1){
            log.info("用户名 手机号 邮箱 对应的帐号不唯一");
            throw new BusinessException("用户名 手机号 邮箱 对应的帐号不唯一");
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(users.get(0),userDTO);
        return userDTO;
    }
}
