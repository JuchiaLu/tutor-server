package com.juchia.tutor.auth.base.authentication.code.core;

import javax.servlet.http.HttpServletRequest;

public interface Repository {

    void save(HttpServletRequest request, String type, ValidateCode validateCode);

    ValidateCode get(HttpServletRequest request, String type);

    void remove(HttpServletRequest request, String type);

}
