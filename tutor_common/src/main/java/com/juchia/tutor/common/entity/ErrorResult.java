package com.juchia.tutor.common.entity;

import com.juchia.tutor.common.enums.ErrorResultEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorResult<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public ErrorResult(ErrorResultEnum status){
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public ErrorResult(ErrorResultEnum status, T date){
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = date;
    }

}
