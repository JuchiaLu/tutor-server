package com.juchia.tutor.auth.front.service;

import com.juchia.tutor.api.business.bo.FeignTeacherBO1;
import com.juchia.tutor.api.business.client.IFeignTeacher;
import com.juchia.tutor.api.upms.bo.FeignUserBO1;
import com.juchia.tutor.api.upms.client.IFeignUser;
import com.juchia.tutor.api.upms.vo.FeignUserInfo;
import com.juchia.tutor.api.upms.vo.FeignUserVO;
import com.juchia.tutor.auth.common.exception.BusinessException;
import com.juchia.tutor.auth.front.entity.bo.UserBO1;
import com.juchia.tutor.auth.front.entity.dto.UserDTO;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    IFeignUser iFeignUser;

    @Autowired
    IFeignTeacher iFeignTeacher;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Transactional
    public UserDTO save(UserBO1 userBO) {

//       根据用户名（包括邮箱，手机号）获取用户信息
        FeignUserInfo has = iFeignUser.getUserInfo(userBO.getUsername());
        if(has!=null){
            throw new BusinessException("用户名已存在");
        }

//      远程保存用户
        FeignUserBO1 feignUserBO = new FeignUserBO1();
        feignUserBO.setPassword(passwordEncoder.encode(userBO.getPassword()));
        feignUserBO.setUsername(userBO.getUsername());
        FeignUserVO feignUserVO = iFeignUser.saveUser(feignUserBO);

//      远程新建一个老师
        FeignTeacherBO1 feignUserBO1 = new FeignTeacherBO1();
        feignUserBO1.setUserId(feignUserVO.getId());
        iFeignTeacher.saveTeacher(feignUserBO1);

        return BeanCopyUtils.copyBean(feignUserVO, UserDTO.class);
    }


    public boolean usernameIsExist(String username) {
        FeignUserInfo has = iFeignUser.getUserInfo(username);
        if(has==null){
            return false;
        }
        return true;
    }
}
