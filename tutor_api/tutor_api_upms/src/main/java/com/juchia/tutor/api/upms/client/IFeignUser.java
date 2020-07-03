package com.juchia.tutor.api.upms.client;


import com.juchia.tutor.api.upms.bo.FeignUserBO1;
import com.juchia.tutor.api.upms.vo.FeignUserVO;
import com.juchia.tutor.api.upms.vo.FeignUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("upms")
public interface IFeignUser {


    // 根据 用户名 或 邮箱号 或 手机号 查询 用户信息（包括权限和角色）
    @GetMapping("/feign/users/userInfo")
    FeignUserInfo getUserInfo(@RequestParam("username")String username);

//  新添用户
    @PostMapping("/feign/users")
    FeignUserVO saveUser(@RequestBody FeignUserBO1 user);

    /**
     * 覆盖修改单个用户
     */
    @PutMapping("/feign/users")
    FeignUserVO updateUser(@RequestBody FeignUserBO1 user);

    /**
     * 部分修改单个用户
     */
    @PatchMapping("/feign/users")
    FeignUserVO patchUser(@RequestBody FeignUserBO1 user);


    // 根据 用户名 或 邮箱号 或 手机号 查询 用户信息（包括权限和角色）
    @GetMapping("/feign/users/userInfo/id")
    FeignUserInfo getUserInfoById(@RequestParam("id")Long id);

}
