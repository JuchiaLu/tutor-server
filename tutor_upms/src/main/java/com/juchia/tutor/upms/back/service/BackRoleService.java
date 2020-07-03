package com.juchia.tutor.upms.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.upms.back.entity.bo.RoleBO1;
import com.juchia.tutor.upms.back.entity.dto.RoleDTO;
import com.juchia.tutor.upms.back.entity.query.RoleQuery1;
import com.juchia.tutor.upms.common.entity.po.Role;
import com.juchia.tutor.upms.common.exception.BusinessException;
import com.juchia.tutor.upms.common.exception.ResourceNotFondException;
import com.juchia.tutor.upms.common.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BackRoleService extends ServiceImpl<RoleMapper, Role> {


    @Autowired
    BackRolePermissionService backRolePermissionService;


    public RoleDTO saveRole(RoleBO1 role) {
        Role condition = new Role();
        condition.setName(role.getName());

//        允许名字相同
//        int count = count(new QueryWrapper<>(condition));
//        if (count>0) {
//            log.info("角色名已存在:{}",role.getName());
//            throw new BusinessException("角色名已存在!");
//        }

        Role saving = new Role();
        BeanUtils.copyProperties(role,saving);
//        字段处理
        LocalDateTime now = LocalDateTime.now();
        saving.setCreateTime(now);
        saving.setUpdateTime(now);
//        saving.setRoleId(null); TODO 在service层处理还是controller层?

        save(saving);

        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(saving,roleDTO);

        return roleDTO;
    }


    public RoleDTO updateRole(RoleBO1 role) {
        Role has = getById(role.getId());
        if(has==null){
            log.info("角色不存在:{}",role.getName());
            throw new BusinessException("角色不存在!");
        }

        Role updating = new Role();
        BeanUtils.copyProperties(role,updating);

        updating.setCreateTime(has.getCreateTime()); //不允许更新创建时间
        updating.setUpdateTime(LocalDateTime.now()); //自动生成更新时间

        updateById(updating);

        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(updating,roleDTO);

        return roleDTO;
    }


    public RoleDTO getRoleByName(String name) throws ResourceNotFondException {
        Role condition = new Role();
        condition.setName(name);

        Role has = getOne(new QueryWrapper<>(condition), false);

        if(has==null){
            log.info("资源不存在: name={}",name);
            throw new ResourceNotFondException("资源不存在: name="+name);
        }

        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(has,roleDTO);
        return roleDTO;
    }


    public void removeRoleById(Long id) {
        removeById(id);
        backRolePermissionService.removeByRoleId(id);
    }


    public void removeRolesByIds(List<Long> ids) {
        if(ids.size()==0){
            return;
        }
        removeByIds(ids);
        backRolePermissionService.removeByRoleIds(ids);
    }


    public IPage<RoleDTO> pageRoles(Page<Role> pageQuery, RoleQuery1 roleQuery) {
        IPage<Role> page = page(pageQuery, roleQuery.buildQueryWrapper());

        List<RoleDTO> roleDTOs = page.getRecords().stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(role,roleDTO);
            return roleDTO;
        }).collect(Collectors.toList());

        IPage<RoleDTO> roleDTOIPage = new Page<>();
        BeanUtils.copyProperties(page,roleDTOIPage);
        roleDTOIPage.setRecords(roleDTOs);

        return roleDTOIPage;
    }


    public RoleDTO getRoleById(Long id) throws ResourceNotFondException {
        Role role = getById(id);
        if(role==null){
            log.info("资源不存在: id={}",id);
            throw new ResourceNotFondException("资源不存在: id="+id);
        }
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(role,roleDTO);
        return roleDTO;
    }


    public List<RoleDTO> listRoles(RoleQuery1 roleQuery) {
        List<Role> roles = list(roleQuery.buildQueryWrapper());
        List<RoleDTO> roleDTOs = roles.stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(role,roleDTO);
            return roleDTO;
        }).collect(Collectors.toList());

        return roleDTOs;
    }


    public RoleDTO patchRole(RoleBO1 role) {

        Role updating = new Role();
        BeanUtils.copyProperties(role,updating);

        updating.setCreateTime(null); //不允许更新创建时间
        updating.setUpdateTime(LocalDateTime.now()); //自动生成更新时间

        if(!updateById(updating)){
            log.info("角色不存在:{}",role.getName());
            throw new BusinessException("角色不存在!");
        }

        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(updating,roleDTO);

        return roleDTO;
    }
}
