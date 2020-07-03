package com.juchia.tutor.auth.base.authentication.mobile;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

/**
 * 短信登录验证逻辑
 * 
 * 由于短信验证码的验证在过滤器里已完成，这里不需要检验密码。
 *
 */
public class SmsCodeAuthenticationProvider extends AbstractSmsUserDetailsAuthenticationProvider {

    private UserDetailsService userDetailsService;

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    @Override
    protected UserDetails retrieveUser(String mobileNumber, SmsCodeAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(mobileNumber);
        }
        catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, SmsCodeAuthenticationToken authentication) throws AuthenticationException {
//         Do Nothing，短信认证不需要校验密码
    }


    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }
}
