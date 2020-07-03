package com.juchia.tutor.upms.feign.controller;


import com.juchia.tutor.api.upms.bo.FeignUserBO1;
import com.juchia.tutor.api.upms.client.IFeignUser;
import com.juchia.tutor.api.upms.vo.FeignUserVO;
import com.juchia.tutor.api.upms.vo.FeignUserInfo;
import com.juchia.tutor.upms.auth.entity.dto.UserDTO1;
import com.juchia.tutor.upms.common.entity.po.Permission;
import com.juchia.tutor.upms.common.entity.po.Role;
import com.juchia.tutor.upms.common.entity.po.User;
import com.juchia.tutor.upms.common.validate.group.Insert;
import com.juchia.tutor.upms.common.validate.group.Patch;
import com.juchia.tutor.upms.common.validate.group.Update;
import com.juchia.tutor.upms.feign.service.FeignUserService;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 * 从 service中接受DTO 将DTO转换成VO返回给前端
 * 有时VO可以省略，直接把DTO返回给前端，前端再自己根据字段转换成要显示的内容，如将 1 转换成男 2 转成女
 * 可以用DTO替代VO的地方，可以省略掉VO，DTO转VO交给前端处理
 * @author juchia
 * @since 2019-08-23
 */
@Validated
@RestController
@RequestMapping("/feign/users") //TODO 服务间调用是否经过网关?答：经过
@Slf4j
public class FeignUserController implements IFeignUser {

    @Autowired
    private FeignUserService feignUserService;

    /**
     *  根据用户名 或 邮箱 或 手机号获取用户信息, 包括 User role permission 的UserInfo
     */
    @Override
    @GetMapping("userInfo")
    public FeignUserInfo getUserInfo(String username){
        UserDTO1 userDTO1 = feignUserService.getUserInfoByUsername(username);
        if(userDTO1==null){
            return null;
        }
        FeignUserInfo feignUserInfo = BeanCopyUtils.copyBean(userDTO1,FeignUserInfo.class);
        feignUserInfo.setRolesName(userDTO1.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        feignUserInfo.setPermissionsValue(userDTO1.getPermissions().stream().map(Permission::getValue).collect(Collectors.toList()));
        return feignUserInfo;
    }

    /**
     * 新增用户
     * @param
     * @return
     */
    @Override
    @PostMapping
    public FeignUserVO saveUser(@Validated(Insert.class) FeignUserBO1 feignUserBO) {
        User po = feignUserService.saveUser(feignUserBO);
        return BeanCopyUtils.copyBean(po,FeignUserVO.class);
    }

//    /**
//     * 用户注册时校验
//     * @param username
//     * @return
//     */
//    @GetMapping("existed/{username}")
//    public FeignUserExisted checkUsername(@PathVariable("username") String username) {
//        FeignUserExisted data = new FeignUserExisted();
//        try {
//            feignUserService.getUserByUsername(username);
//            data.setExisted(true);
//        } catch (ResourceNotFondException e) {
//            data.setExisted(false);
//        }
//        return data;
//    }


    /**
     * 覆盖修改单个用户
     */
    @PutMapping
    public FeignUserVO updateUser(@Validated(Update.class) @RequestBody FeignUserBO1 user){
        return feignUserService.updateUser(user);
    }

    /**
     * 部分修改单个用户
     */
    @PatchMapping
    public FeignUserVO patchUser(@Validated(Patch.class) @RequestBody FeignUserBO1 user){
        return feignUserService.patchUser(user);
    }

    @GetMapping("userInfo/id")
    public FeignUserInfo getUserInfoById(@RequestParam("id")Long id){
        UserDTO1 userDTO1 = feignUserService.getUserInfoById(id);
        if(userDTO1==null){
            return null;
        }
        FeignUserInfo feignUserInfo = BeanCopyUtils.copyBean(userDTO1,FeignUserInfo.class);
        feignUserInfo.setRolesName(userDTO1.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        feignUserInfo.setPermissionsValue(userDTO1.getPermissions().stream().map(Permission::getValue).collect(Collectors.toList()));
        return feignUserInfo;
    }

}

