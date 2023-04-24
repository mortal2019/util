package com.mortal2019.exception_model;

import com.mortal2019.exception.BaseException;

/**
 * Result解析报错
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 14:21
 */
public class ResultException extends BaseException {
    public ResultException(String message) {
        super(message);
    }
}
