package com.mortal2019.weixin.base;

import java.io.Serializable;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/26 11:49
 */
public class BaseDomain implements Serializable {
    /**
     * 过期时间
     */
    private final long expiresIn;

    protected BaseDomain(long expiresIn) {
        this.expiresIn = System.currentTimeMillis() + (expiresIn * 1000);
    }

    /**
     * 检查是否过期
     *
     * @return true: 已过期, false: 未过期
     */
    public boolean checkExpiresIn() {
        return System.currentTimeMillis() > expiresIn;
    }
}
