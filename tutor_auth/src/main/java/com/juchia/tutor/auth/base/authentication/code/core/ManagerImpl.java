package com.juchia.tutor.auth.base.authentication.code.core;

import com.juchia.tutor.auth.base.authentication.code.support.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ManagerImpl implements Manager {

    @Autowired
    List<Provider> providers;

    @Autowired
    Repository repository;

    @Override
    public void create(HttpServletRequest request, HttpServletResponse response, String type) {

        Optional<Provider> first = providers.stream().filter(provider -> {
            return provider.support().equalsIgnoreCase(type);
        }).findFirst();

        if(!first.isPresent()){
            throw new ValidateCodeException("不支持的验证码类型:" + type);
        }

        Provider provider = first.get();

//        生成验证码
        ValidateCode code = provider.generate(request);

        //        保存验证码
        repository.save(request,type,code);

//        发送验证码 TODO 这里有 BUG , 如图形验证码, 先把IO流发送到前端, 保存时才报错,可以先检测 requestId 是否存在
        provider.sent(request,response,code);

    }

    @Override
    public void validate(HttpServletRequest request, String type){

//        TODO 可配置 且每种校验码的参数可以不一样, 这样同一个请求才能同时配置多种校验码
        String validateCodeParameter = "code";

//        保存的校验码
        ValidateCode saveCode = repository.get(request, type);

//        请求中的校验码
        String validateCode;

        try {
            //尝试从请求中拿到验证码
            validateCode = ServletRequestUtils.getStringParameter(request, validateCodeParameter);
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException(type + "验证码读取失败,请求中未带有参数: "+ validateCodeParameter);
        }

        if (validateCode==null||validateCode.isEmpty()) {
            throw new ValidateCodeException(type + "验证码的值不能为空");
        }

        if (saveCode == null) {
            throw new ValidateCodeException(type + "验证码未生成");
        }

        if (LocalDateTime.now().isAfter(saveCode.getExpiredTime())) {
            repository.remove(request, type);
            throw new ValidateCodeException(type + "验证码已过期");
        }

        if (!saveCode.getCode().equalsIgnoreCase(validateCode)) {
            throw new ValidateCodeException(type + "验证码不匹配");
        }
        repository.remove(request, type);
    }
}
