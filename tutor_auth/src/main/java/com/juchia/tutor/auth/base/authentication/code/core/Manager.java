package com.juchia.tutor.auth.base.authentication.code.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Manager {

    void create(HttpServletRequest request, HttpServletResponse response, String type);

    void validate(HttpServletRequest request, String type);

}
