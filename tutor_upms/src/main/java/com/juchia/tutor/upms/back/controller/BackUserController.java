package com.juchia.tutor.upms.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juchia.tutor.upms.back.entity.bo.UserBO1;
import com.juchia.tutor.upms.back.entity.dto.UserDTO;
import com.juchia.tutor.upms.back.entity.dto.UserExisted;
import com.juchia.tutor.upms.back.entity.dto.UserInfo;
import com.juchia.tutor.upms.back.entity.query.UserQuery1;
import com.juchia.tutor.upms.back.service.BackUserService;
import com.juchia.tutor.upms.common.entity.po.User;
import com.juchia.tutor.upms.common.exception.ResourceNotFondException;
import com.juchia.tutor.upms.common.validate.group.Insert;
import com.juchia.tutor.upms.common.validate.group.Patch;
import com.juchia.tutor.upms.common.validate.group.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("back/users")
@Slf4j
public class BackUserController {


    @Autowired
    private BackUserService backUserService;

    /**
     * 增加单个用户
     * @param
     * @param
     * @return 新添加的用户
     * 状态码：
     *          字段校验失败：400，填写时前端也校验过
     *          用户名已存在：业务异常，且填写时已远程校验过
     *          成功创建：200 还是 201
     * TODO 返回新增的id 还是 返回整个User?
     */
    @PreAuthorize("hasAuthority('upms:user:insert')")
    @PostMapping
    public UserDTO saveUser(@Validated(Insert.class) @RequestBody UserBO1 userBO){
        userBO.setId(null);
        return backUserService.saveUser(userBO);
    }


    /**
     * 根据Id删除单个用户
     * TODO 删除是否需要把删除成功的用户返回去，各大公司的都没有返回，原因是什么?
     */
    @PreAuthorize("hasAuthority('upms:user:delete')")
    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeUserById(@PathVariable("id") Long id){
        backUserService.removeUserById(id);
    }

    /**
     * 根据Ids删除多个用户
     */
    @PreAuthorize("hasAuthority('upms:user:delete')")
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeUsersByIds( @RequestBody List<Long> ids){
        backUserService.removeUsersByIds(ids);
    }

    /**
     * 覆盖修改单个用户
     */
    @PreAuthorize("hasAuthority('upms:user:update')")
    @PutMapping
    public UserDTO updateUser(@Validated(Update.class) @RequestBody UserBO1 user){
        return backUserService.updateUser(user);
    }

    /**
     * 部分修改单个用户
     */
    @PreAuthorize("hasAuthority('upms:user:update')")
    @PatchMapping
    public UserDTO patchUser(@Validated(Patch.class) @RequestBody UserBO1 user){
        return backUserService.patchUser(user);
    }

    /**
     * 根据id查询单个用户
     * @return
     */
    @PreAuthorize("hasAuthority('upms:user:select')")
    @GetMapping("{id}")
    public UserDTO getUserById( @PathVariable("id") Long  id) throws ResourceNotFondException {
        return backUserService.getUserById(id);
    }


    /**
     * 根据用户名查询单个用户
     * @param username
     * @return
     */
    @PreAuthorize("hasAuthority('upms:user:select')")
    @GetMapping("username/{username}")
    public UserDTO getUserByUsername(@PathVariable("username") String username) throws ResourceNotFondException {
        return backUserService.getUserByUsername(username);
    }

    /**
     * 多条件查询+分页+排序
     * TODO 直接使用 mybatis plus 提供的Page还是自定义一个PageVO？
     * TODO 不能直接返回数据层的 User 要返回 UserView 去掉密码等字段，防止字段暴露
     * 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:user:select')")
    @GetMapping("page")
    public IPage<UserDTO> pageUsers(Page<User> pageQuery, UserQuery1 userQuery){
        return backUserService.pageUsers(pageQuery, userQuery);
    }

    /**
     * 不分页条件查询
     * @param userQuery
     * @return
     */
    @PreAuthorize("hasAuthority('upms:user:select')")
    @GetMapping
    public List<UserDTO> listUsers(UserQuery1 userQuery){
        return backUserService.listUsers(userQuery);
    }


//    以下开始测试

    /**
     * 判断用户是否已存在
     * @param username
     * @return
     */
    @PreAuthorize("hasAuthority('upms:user:select')")
    @GetMapping("existed/{username}")
    public UserExisted checkUsername(@PathVariable("username") String username) {
        UserExisted data = new UserExisted();
        try {
            backUserService.getUserByUsername(username);
            data.setExisted(true);
        } catch (ResourceNotFondException e) {
            data.setExisted(false);
        }
        return data;
    }

    /**
     *  获取登录用户信息, 包括 User role permission
     */
    @PreAuthorize("hasAuthority('upms:user:select')")
    @GetMapping("userInfo")
    public UserInfo getUserInfo() throws ResourceNotFondException {
        String username = null;
        return backUserService.getUserInfoByUsername(username);
    }

}

