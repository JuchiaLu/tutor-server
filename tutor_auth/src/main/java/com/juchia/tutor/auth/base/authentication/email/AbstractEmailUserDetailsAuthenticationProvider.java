package com.juchia.tutor.auth.base.authentication.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.util.Assert;

/**
 * 短信登录验证逻辑抽象类
 *
 */
public abstract class AbstractEmailUserDetailsAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

//    日志
    protected final Log logger = LogFactory.getLog(getClass());

//  国际化消息类
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

//  登录用户缓存
    private UserCache userCache = new NullUserCache();

//  强制将 Principal 对象当 String 看待，我们的 Principal 是手机号码，且本来就是 String 类型
    private boolean forcePrincipalAsString = false;

//  隐藏用户不存在异常，如我们有需求，不能让别人知道系统中是用户是不存在还是密码错了
    protected boolean hideUserNotFoundExceptions = true;

//  UserDetails 里面的用户状态是用布尔值表示，要用 UserDetailsChecker 来校验布尔值，抛相对的异常
    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();

//  权限集合转换的工具类
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

//  抽象方法，额外的校验
    protected abstract void additionalAuthenticationChecks(UserDetails userDetails, EmailCodeAuthenticationToken authentication) throws AuthenticationException;

//  属性设置后要额外做的工作
    public final void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userCache, " userCache 必须设置");
        Assert.notNull(this.messages, "message source 必须设置");
        doAfterPropertiesSet();
    }

//  主要认证逻辑
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

//      校验 Token 类型，是否是这个类支持的, 重写这给类就是因为这行的EmailCodeAuthenticationToken.class
        Assert.isInstanceOf(EmailCodeAuthenticationToken.class, authentication, messages.getMessage("AbstractEmailUserDetailsAuthenticationProvider.onlySupports", "Only EmailCodeAuthenticationToken is supported"));

//      获取邮箱号
        String eamil = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

//      尝试中缓存中拿出user
        UserDetails user = this.userCache.getUserFromCache(eamil);

//      缓存是否被使用
        boolean cacheWasUsed = true;

//      缓存中没有user
        if (user == null) {
//           将缓存被使用置为 false
            cacheWasUsed = false;
            try {
//              retrieveUser 方法调用 UserDetails 从数据库找 user
                user = retrieveUser(eamil, (EmailCodeAuthenticationToken) authentication);
            }
            catch (UsernameNotFoundException notFound) {
                logger.debug("User '" + eamil + "' not found");

//               如果已经设置了隐藏用户找不到异常
                if (hideUserNotFoundExceptions) {

//                  抛密码不正确异常（这里可以按需改造，短信认证用不到密码，我们直接抛用户不存在即可）
                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractEmailUserDetailsAuthenticationProvider.badCredentials",
                            "密码错误"));
                }
                else {
//                  如果没设置了隐藏用户找不到异常，往上抛即可
                    throw notFound;
                }
            }

//          不能返回null，只能抛异常
            Assert.notNull(user, "retrieveUser 方法返回 null，违反接口契约，有问题就抛异常，没问题返回数据，不要返回 null");
        }

//      校验 user，这个 user 可能是缓存中的，也可能是 数据库中查出来的
        try {
//          前置校验，主要用来检测 UserDetails 中的用户状态布尔值，然后抛异常，请看下面的内部类
            preAuthenticationChecks.check(user);

//          额外校验，这个是抽象方法，具体校验什么请看子类怎么实现，如校验密码等
            additionalAuthenticationChecks(user, (EmailCodeAuthenticationToken) authentication);
        }
        catch (AuthenticationException exception) {
//          user 校验有异常，且是缓存中的user 那么我们再从数据库中查找用户
            if (cacheWasUsed) {
//              将缓存被使用置为 false
                cacheWasUsed = false;

//              retrieveUser 方法调用 UserDetails 从数据库找 user
                user = retrieveUser(eamil, (EmailCodeAuthenticationToken) authentication);

//              前置校验，主要用来检测 UserDetails 中的用户状态布尔值，然后抛异常，请看下面的内部类
                preAuthenticationChecks.check(user);

//              额外校验，这个是抽象方法，具体校验什么请看子类怎么实现，如校验密码等
                additionalAuthenticationChecks(user, (EmailCodeAuthenticationToken) authentication);
            }
//          不是缓存中的 user 有异常，直接往上抛
            else {
                throw exception;
            }
        }

//      后置校验，也是用来检测 UserDetails 中的用户状态布尔值，然后抛异常，请看下面的内部类
        postAuthenticationChecks.check(user);

//      user 不是通过 缓存中的 user 校验成功的，将这个校验成功的 user 放到缓存中
        if (!cacheWasUsed) {
            this.userCache.putUserInCache(user);
        }

        Object principalToReturn = user;

        if (forcePrincipalAsString) {
//          将 principal 强制转换成 String
            principalToReturn = user.getUsername();
        }

        return createSuccessAuthentication(principalToReturn, authentication, user);
    }


//  认证成功后，重新 new 一个认证成功的 token
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        EmailCodeAuthenticationToken result = new EmailCodeAuthenticationToken(principal, authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        return result;
    }

    protected void doAfterPropertiesSet() throws Exception {
    }

    public boolean supports(Class<?> authentication) {
        return (EmailCodeAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                logger.debug("User account is locked");

                throw new LockedException(messages.getMessage(
                        "AbstractEmailUserDetailsAuthenticationProvider.locked",
                        "User account is locked"));
            }

            if (!user.isEnabled()) {
                logger.debug("User account is disabled");

                throw new DisabledException(messages.getMessage(
                        "AbstractEmailUserDetailsAuthenticationProvider.disabled",
                        "User is disabled"));
            }

            if (!user.isAccountNonExpired()) {
                logger.debug("User account is expired");

                throw new AccountExpiredException(messages.getMessage(
                        "AbstractEmailUserDetailsAuthenticationProvider.expired",
                        "User account has expired"));
            }
        }
    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                logger.debug("User account credentials have expired");

                throw new CredentialsExpiredException(messages.getMessage(
                        "AbstractEmailUserDetailsAuthenticationProvider.credentialsExpired",
                        "User credentials have expired"));
            }
        }
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected abstract UserDetails retrieveUser(String username, EmailCodeAuthenticationToken authentication) throws AuthenticationException;


    public UserCache getUserCache() {
        return userCache;
    }

    public boolean isForcePrincipalAsString() {
        return forcePrincipalAsString;
    }

    public boolean isHideUserNotFoundExceptions() {
        return hideUserNotFoundExceptions;
    }

    public void setForcePrincipalAsString(boolean forcePrincipalAsString) {
        this.forcePrincipalAsString = forcePrincipalAsString;
    }


    public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
        this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
    }


    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }


    protected UserDetailsChecker getPreAuthenticationChecks() {
        return preAuthenticationChecks;
    }


    public void setPreAuthenticationChecks(UserDetailsChecker preAuthenticationChecks) {
        this.preAuthenticationChecks = preAuthenticationChecks;
    }

    protected UserDetailsChecker getPostAuthenticationChecks() {
        return postAuthenticationChecks;
    }

    public void setPostAuthenticationChecks(UserDetailsChecker postAuthenticationChecks) {
        this.postAuthenticationChecks = postAuthenticationChecks;
    }

    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
        this.authoritiesMapper = authoritiesMapper;
    }
}
