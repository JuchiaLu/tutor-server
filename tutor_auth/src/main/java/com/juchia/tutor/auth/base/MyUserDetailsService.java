package com.juchia.tutor.auth.base;

import com.juchia.tutor.api.upms.client.IFeignUser;
import com.juchia.tutor.api.upms.vo.FeignUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 具体实现 @see JdbcDaoImpl
 */
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@Autowired
    //private PasswordEncoder passwordEncoder;


    @Autowired
    private IFeignUser restUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FeignUserInfo userInfo = restUserService.getUserInfo(username);
        if(userInfo==null){
            throw new UsernameNotFoundException("用户不存在"+username);
        }
        return buildUser(userInfo);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userid) throws UsernameNotFoundException {

//        TODO 暂时用username来替代userId

        logger.info("社交登录" + userid);

        FeignUserInfo userInfo = restUserService.getUserInfoById(Long.valueOf(userid));
        if(userInfo==null){
            throw new UsernameNotFoundException("用户不存在"+userid);
        }

        UserDetails user = buildUser(userInfo);

        SocialUser socialUser = new SocialUser(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                user.getAuthorities()
                );
        return socialUser;
    }


    private UserDetails buildUser(FeignUserInfo userInfo){
        List<GrantedAuthority> authorities= new ArrayList<>();
        List<String> roles = userInfo.getRolesName();
        List<String> permissions = userInfo.getPermissionsValue();

        roles.forEach(role->{
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        });

        permissions.forEach(permission->{
            authorities.add(new SimpleGrantedAuthority(permission));
        });


        UserDetails user = User.builder()
//                .username(userInfo.getUsername())
                .username(String.valueOf(userInfo.getId())) //TODO 这里为了方便,将userId当作Username
                .password(userInfo.getPassword())
               // .disabled(userInfo.getStatus() == 0)
                .authorities(authorities).build();

        return user;
    }



//    private SocialUserDetails buildUser(String userId) {
//        // 根据用户名查找用户信息
//        //根据查找到的用户信息判断用户是否被冻结
//        String password = passwordEncoder.encode("123456");
//        logger.info("数据库密码是:"+password);
//
//        return new SocialUser(userId, password,
//                true, true, true, true,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("xxx"));
//    }

}
