package com.juchia.tutor.upms.common.handler;

import com.juchia.tutor.common.entity.ErrorResult;
import com.juchia.tutor.common.enums.ErrorResultEnum;
import com.juchia.tutor.upms.common.exception.BusinessException;
import com.juchia.tutor.upms.common.exception.ResourceNotFondException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 400异常
    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            ServletRequestBindingException.class,
            BindException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult<Map<String,String>> handleValidationException(Exception e) {
        Map<String,String> errorsMap = new HashMap<>();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException t = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = t.getBindingResult();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorsMap.put(fieldError.getField(),fieldError.getDefaultMessage());
            });
            log.info("post请求参数绑定到实体类中，参数校验失败");
        } else if (e instanceof BindException) {
            BindException t = (BindException) e;
            BindingResult bindingResult = t.getBindingResult();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorsMap.put(fieldError.getField(),fieldError.getDefaultMessage());
            });
            log.info("get请求参数绑定到实体类中，参数校验失败");
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException t = (ConstraintViolationException) e;
            t.getConstraintViolations().forEach(ConstraintViolations->{
                errorsMap.put(ConstraintViolations.getPropertyPath().toString(),ConstraintViolations.getMessage());
            });
            log.info("路径参数出错");
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException t = (MissingServletRequestParameterException) e;
            errorsMap.put( t.getParameterName(),t.getMessage());
            log.info("请求参数不能为空");
        } else if (e instanceof MissingPathVariableException) {
            MissingPathVariableException t = (MissingPathVariableException) e;
            errorsMap.put( t.getVariableName(),t.getMessage());
            log.info("请求路径参数不能为空");
        } else {
            e.printStackTrace();
            log.info("未知参数错误");
        }
        return new ErrorResult<>(ErrorResultEnum.INVALID_PARAMETER,errorsMap);
    }


    //业务异常
    @ExceptionHandler({
            BusinessException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult<Map<String,String>> handleValidationException(BusinessException e) {
        Map<String,String> errorsMap = new HashMap<>();
        errorsMap.put("error",e.getMessage());
        return new ErrorResult<>(ErrorResultEnum.BUSINESS_ERROR,errorsMap);
    }

    //TODO 资源不存在 404异常
    @ExceptionHandler({
            ResourceNotFondException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult<Map<String,String>> handleResourceNotFondException(ResourceNotFondException e) {
        Map<String,String> errorsMap = new HashMap<>();
        errorsMap.put("error",e.getMessage());
        return new ErrorResult<>(ErrorResultEnum.RESOURCE_NOT_FOND,errorsMap);
    }

    //TODO 未认证 401异常 AuthenticationException， 这里不能这样，这个异常这里捕获不到，不经过severlet，要自定义处理器抛
//    首先，你要明白，spring security的工作原理，他是基于filter，
// 也就是说他抛出的异常的时候，请求还没有到达我们的servlet,
// 用你的那种方式使用dispatchServlet的异常捕获机制当然捕获不到异常
//    @ExceptionHandler({
//            AuthenticationException.class
//    })
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResult<Map<String,String>> handleAuthenticationException(AuthenticationException e) {
//        Map<String,String> errorsMap = new HashMap<>();
//        errorsMap.put("error",e.getMessage());
//        return new ErrorResult<>(ErrorResultEnum.UNAUTHENTICATED,errorsMap);
//    }

    //TODO 未授权 403异常 AccessDeniedException
    @ExceptionHandler({
            AccessDeniedException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResult<Map<String,String>> handleAccessDeniedException(AccessDeniedException e) {
        Map<String,String> errorsMap = new HashMap<>();
        errorsMap.put("error",e.getMessage());
        return new ErrorResult<>(ErrorResultEnum.UNAUTHORISE,errorsMap);
    }
    //TODO 方法不允许 405异常
    //TODO 服务器错误 500异常

}
