package com.mortal2019.exception;

import com.mortal2019.constant.ResultEnum;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义错误类
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 14:12
 */
public class BaseException extends RuntimeException{
    private Integer errId;
/*
    public ArchivesManagementException(Integer code, String message) {
        super(message);
        this.code = code;
    }
*/

    public BaseException(@NotNull ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.errId = resultEnum.getCode();
    }

    public BaseException(@NotNull ResultEnum resultEnum, String message) {
        super(resultEnum.getMessage() + ":" + message);
        this.errId = resultEnum.getCode();
    }

    public BaseException(String message, @NotNull ResultEnum resultEnum) {
        super(message + resultEnum.getMessage());
        this.errId = resultEnum.getCode();
    }

    public BaseException(String message, @NotNull ResultEnum resultEnum, String message1) {
        super(message + resultEnum.getMessage() + message1);
        this.errId = resultEnum.getCode();
    }

    public BaseException(String message) {
        super(message);
        this.errId = 0;
    }

    public Integer getErrId() {
        return errId;
    }

    public void setErrId(Integer errId) {
        this.errId = errId;
    }
}
