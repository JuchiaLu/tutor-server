package com.juchia.tutor.common.enums;

/**
 * TODO 按系统模块定代码规则
 */
public enum ErrorResultEnum {


//    请求错误 10000-20000
        INVALID_PARAMETER(10001,"非法参数！"),
        UNAUTHENTICATED(10002,"未认证！"),
        UNAUTHORISE(10003,"未授权！"),
        RESOURCE_NOT_FOND(10004,"资源不存在!"),


//    业务错误 20000-30000
       BUSINESS_ERROR(20001,"业务错误"),



//    服务器错误 30000-40000
        SERVER_ERROR(30001,"服务器错误！"),


//    未知错误
        UNKNOWN_ERROR(99999,"未知错误！");



        //操作代码
        private final int code;
        //提示信息
        private final String message;

        private ErrorResultEnum( int code, String message){
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }
}
