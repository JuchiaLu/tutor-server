package com.juchia.tutor.auth.base.authentication.code.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {

    @Autowired
    private Manager manager;

    @GetMapping("code/{type}")
    public void getCode(HttpServletRequest request, HttpServletResponse response, @PathVariable("type") String type){
        manager.create(request, response, type);
    }
}
