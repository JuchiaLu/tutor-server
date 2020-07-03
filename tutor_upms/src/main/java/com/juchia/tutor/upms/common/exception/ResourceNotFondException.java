package com.juchia.tutor.upms.common.exception;

public class ResourceNotFondException extends Exception {
    public ResourceNotFondException (String msg){
        super(msg);
    }
    public ResourceNotFondException (String msg, Throwable t){
        super(msg,t);
    }
}
