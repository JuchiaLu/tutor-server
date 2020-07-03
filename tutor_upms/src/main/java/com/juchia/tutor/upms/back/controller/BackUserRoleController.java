package com.juchia.tutor.upms.back.controller;


import com.juchia.tutor.upms.back.entity.dto.RoleDTO;
import com.juchia.tutor.upms.back.service.BackUserRoleService;
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
public class BackUserRoleController {

   @Autowired
   BackUserRoleService backUserRoleService;

    /**
     * 根据用户id查询用户的角色
     */
    @PreAuthorize("hasAuthority('upms:userRole:select')")
    @GetMapping("back/users/{userId}/roles")
    List<RoleDTO> listRolesByUserId(@PathVariable("userId") Long userId){
        return backUserRoleService.listRolesByUserId(userId);
    }


    /**
     * 根据用户id和角色id列表为用户添加角色
     */
    @PreAuthorize("hasAuthority('upms:userRole:update')")
    @PutMapping("back/users/{userId}/roles")
    void saveUserRolesByUserId(@PathVariable("userId") Long userId,@RequestBody List<Long> roleIds){
        backUserRoleService.saveUserRolesByUserId(userId,roleIds);
    }


}

