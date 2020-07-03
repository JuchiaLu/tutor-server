package com.juchia.tutor.business.common.validate.validator;


import com.juchia.tutor.business.common.validate.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//校验注解的类必须实现ConstraintValidator，第一个泛型是注解，第二个泛型是校验参数的类型（手机号是String类型）
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private String regexp;

    //初始化方法
    @Override
    public void initialize(Phone constraintAnnotation) {
        //获取校验的手机号的格式
        this.regexp = constraintAnnotation.regexp();
    }

    //value是@Phone注解所注解的字段值
    //校验，返回true则通过校验，返回false则校验失败，错误信息为注解中的message
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }

        return value.matches(regexp);
    }
}
