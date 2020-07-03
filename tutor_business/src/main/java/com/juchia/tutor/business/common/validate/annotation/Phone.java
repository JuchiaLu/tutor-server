package com.juchia.tutor.business.common.validate.annotation;


import com.juchia.tutor.business.common.validate.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
//用于校验手机号的逻辑类
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    //手机号的校验格式
    String regexp() default "^[1][3-9][0-9]{9}$";

    //出现错误返回的信息
    String message() default "手机号格式错误";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Phone[] value();
    }
}
