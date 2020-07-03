package com.juchia.tutor.auth.base.authentication.code.support;

import com.juchia.tutor.auth.base.authentication.code.core.ValidateCode;

import java.time.LocalDateTime;

public class DefaultCode implements ValidateCode {

    private String code;
    private LocalDateTime expireTime;


    public DefaultCode(String validateCode, int expireSeconds){
        this.code = validateCode;
        this.expireTime = LocalDateTime.now().plusSeconds(expireSeconds);
    }

    public DefaultCode(String validateCode, LocalDateTime expireTime){
        this.code = validateCode;
        this.expireTime = expireTime;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public LocalDateTime getExpiredTime() {
        return expireTime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
