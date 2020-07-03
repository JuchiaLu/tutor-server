package com.juchia.tutor.auth.base.authentication.code.repository;


import com.juchia.tutor.auth.base.authentication.code.core.Repository;
import com.juchia.tutor.auth.base.authentication.code.core.ValidateCode;
import com.juchia.tutor.auth.base.authentication.code.support.DefaultCode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SessionRepository implements Repository {

    /**
	 * 验证码放入session时的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

	/**
	 * 构建验证码放入session时的key TODO 储存多个相同类型的验证码
	 */
	private String buildKey(HttpServletRequest request, String type) {
		return SESSION_KEY_PREFIX + type.toUpperCase();//如: SESSION_KEY_FOR_CODE_SMS
	}
    @Override
    public void save(HttpServletRequest request,String type, ValidateCode validateCode) {
        ValidateCode savingCode = new DefaultCode(validateCode.getCode(),validateCode.getExpiredTime());
	    request.getSession().setAttribute(buildKey(request,type),savingCode);
    }

    @Override
    public ValidateCode get(HttpServletRequest request, String type) {
        return (ValidateCode) request.getSession().getAttribute(buildKey(request,type));
    }

    @Override
    public void remove(HttpServletRequest request, String type) {
        request.getSession().removeAttribute(buildKey(request,type));
    }
}
