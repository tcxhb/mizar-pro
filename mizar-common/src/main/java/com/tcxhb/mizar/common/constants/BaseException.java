package com.tcxhb.mizar.common.constants;


/**
 * liuyulong
 * 2022/6/14
 */
public class BaseException extends RuntimeException {

    private String code;
    private String msg;


    public BaseException(String errorCode, String msg) {
        super(msg);
        this.code = errorCode;
        this.msg = msg;
    }
    public BaseException(ServiceError errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }
    public BaseException(ServiceError errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
