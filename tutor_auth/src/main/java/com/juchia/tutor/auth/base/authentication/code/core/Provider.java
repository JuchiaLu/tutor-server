package com.juchia.tutor.auth.base.authentication.code.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Provider {

    String support();

    ValidateCode generate(HttpServletRequest request);

    void sent(HttpServletRequest request, HttpServletResponse response,ValidateCode code);

}
