package com.sunzxy.frescopluslib.exception;

/**
 * Created by zhengxiaoyong on 16/1/10.
 */
public class FPRuntimeException extends RuntimeException {
    public FPRuntimeException(String detailMessage) {
        super(detailMessage);
    }

    public FPRuntimeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        initCause(throwable);
    }
}
