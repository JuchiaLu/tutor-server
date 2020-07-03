package com.juchia.tutor.upms.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.upms.back.entity.bo.PermissionBO1;
import com.juchia.tutor.upms.back.entity.dto.PermissionDTO;
import com.juchia.tutor.upms.back.entity.query.PermissionQuery1;
import com.juchia.tutor.upms.common.entity.po.Permission;
import com.juchia.tutor.upms.common.exception.BusinessException;
import com.juchia.tutor.upms.common.exception.ResourceNotFondException;
import com.juchia.tutor.upms.common.mapper.PermissionMapper;
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
public class BackPermissionService extends ServiceImpl<PermissionMapper, Permission>{

    //TODO 服务层不能相互调用, 采用服务层调用多个Dao还是controller调用多个Service还是再加一层 或消息队列解耦?
    // TODO 服务间调用事务的循环如何处理
    @Autowired
    BackRolePermissionService backRolePermissionService;


    public PermissionDTO savePermission(PermissionBO1 permission) {
        Permission condition = new Permission();
        condition.setName(permission.getName());

//        允许名字相同
//        int count = count(new QueryWrapper<>(condition));
//        if (count>0) {
//            log.info("权限名已存在:{}",permission.getName());
//            throw new BusinessException("权限名已存在!");
//        }

        Permission saving = new Permission();
        BeanUtils.copyProperties(permission,saving);
//        字段处理
        LocalDateTime now = LocalDateTime.now();
        saving.setCreateTime(now);
        saving.setUpdateTime(now);
//        saving.setPermissionId(null); TODO 在service层处理还是controller层?

        save(saving);

        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(saving,permissionDTO);

        return permissionDTO;
    }


    public PermissionDTO updatePermission(PermissionBO1 permission) {
        Permission has = getById(permission.getId());
        if(has==null){
            log.info("权限不存在:{}",permission.getName());
            throw new BusinessException("权限不存在!");
        }

        Permission updating = new Permission();
        BeanUtils.copyProperties(permission,updating);

        updating.setCreateTime(has.getCreateTime()); //不允许更新创建时间
        updating.setUpdateTime(LocalDateTime.now()); //自动生成更新时间

        updateById(updating);

        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(updating,permissionDTO);

        return permissionDTO;
    }


    public PermissionDTO getPermissionByName(String name) throws ResourceNotFondException {
        Permission condition = new Permission();
        condition.setName(name);
        Permission has = getOne(new QueryWrapper<>(condition),false);
        if(has==null){
            log.info("资源不存在: name={}",name);
            throw new ResourceNotFondException("资源不存在: name="+name);
            //return null;
        }
        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(has,permissionDTO);
        return permissionDTO;
    }


    public void removePermissionById(Long id) {
//         删除自己
        removeById(id);

//        删除自己和角色表的关联关系
        backRolePermissionService.removeByPermissionId(id);

//          获取子权限
        List<PermissionDTO> children = listPermissionsByParentId(id);

        if(children.size()==0){
            return;
        }

//        删除子权限
        List<Long> ids = children.stream().map(permission -> {
            return permission.getId();
        }).collect(Collectors.toList());
        removeByIds(ids);

//        删除子权限和角色表的关联关系
        backRolePermissionService.removeByPermissionIds(ids);
    }


    public void removePermissionsByIds(List<Long> ids) {
        if(ids.size()==0){
            return;
            //throw new BusinessException("请求列表为空");
        }
        removeByIds(ids);
    }


    public IPage<PermissionDTO> pagePermissions(Page<Permission> pageQuery, PermissionQuery1 permissionQuery) {
        IPage<Permission> page = page(pageQuery, permissionQuery.buildQueryWrapper());

        List<PermissionDTO> permissionDTOs = page.getRecords().stream().map(permission -> {
            PermissionDTO permissionDTO = new PermissionDTO();
            BeanUtils.copyProperties(permission,permissionDTO);
            return permissionDTO;
        }).collect(Collectors.toList());

        IPage<PermissionDTO> permissionDTOIPage = new Page<>();
        BeanUtils.copyProperties(page,permissionDTOIPage);
        permissionDTOIPage.setRecords(permissionDTOs);

        return permissionDTOIPage;
    }

//    以下开始测试


    public List<PermissionDTO> listPermissionsByParentId(Long parentId) {
        Permission condition = new Permission();
        condition.setParentId(parentId);
        List<Permission> permissions = list(new QueryWrapper<>(condition));

        List<PermissionDTO> permissionDTOs = permissions.stream().map(permission -> {
            PermissionDTO permissionDTO = new PermissionDTO();
            BeanUtils.copyProperties(permission,permissionDTO);
            return permissionDTO;
        }).collect(Collectors.toList());

        return permissionDTOs;
    }


    public PermissionDTO getPermissionById(Long id) throws ResourceNotFondException {
        Permission permission = getById(id);
        if(permission==null){
            log.info("资源不存在: id={}",id);
            throw new ResourceNotFondException("资源不存在: id="+id);
            //return null;
        }
        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(permission,permissionDTO);

        return permissionDTO;
    }


    public List<PermissionDTO> listPermissions(PermissionQuery1 permissionQuery) {
        List<Permission> permissions = list(permissionQuery.buildQueryWrapper());

        List<PermissionDTO> permissionDTOs = permissions.stream().map(permission -> {
            PermissionDTO permissionDTO = new PermissionDTO();
            BeanUtils.copyProperties(permission,permissionDTO);
            return permissionDTO;
        }).collect(Collectors.toList());

        return permissionDTOs;
    }


    public PermissionDTO patchPermission(PermissionBO1 permission) {

        Permission updating = new Permission();
        BeanUtils.copyProperties(permission,updating);

        updating.setCreateTime(null); //不允许更新创建时间
        updating.setUpdateTime(LocalDateTime.now()); //自动生成更新时间

        if(!updateById(updating)){
            log.info("权限不存在:{}",permission.getName());
            throw new BusinessException("权限不存在!");
        }

        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(updating,permissionDTO);

        return permissionDTO;
    }
}
