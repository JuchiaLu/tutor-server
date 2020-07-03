package com.juchia.tutor.auth.common.exception;

public class CheckedException extends Exception {

    public CheckedException(String message) {
        super(message);
    }

    public CheckedException (String msg, Throwable t){
        super(msg,t);
    }

}
