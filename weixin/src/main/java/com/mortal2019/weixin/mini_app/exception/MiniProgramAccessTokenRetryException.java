package com.mortal2019.weixin.mini_app.exception;

/**
 * 获取access_token异常报错
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/26 11:35
 */
public class MiniProgramAccessTokenRetryException extends RuntimeException{
    public MiniProgramAccessTokenRetryException() {
    }

    public MiniProgramAccessTokenRetryException(String message) {
        super(message);
    }

    public MiniProgramAccessTokenRetryException(String message, Throwable cause) {
        super(message, cause);
    }

    public MiniProgramAccessTokenRetryException(Throwable cause) {
        super(cause);
    }

    public MiniProgramAccessTokenRetryException(String message, Throwable cause, boolean enableSuppression,
                                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
