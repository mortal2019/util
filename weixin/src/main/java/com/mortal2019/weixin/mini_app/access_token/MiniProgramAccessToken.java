package com.mortal2019.weixin.mini_app.access_token;

import com.mortal2019.weixin.base.BaseDomain;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/26 11:50
 */
public class MiniProgramAccessToken extends BaseDomain {
    /**
     * token的值
     */
    private final String value;


    /**
     * @param value token
     * @param expiresIn 过期时间,单位秒
     */
    public MiniProgramAccessToken(String value, long expiresIn) {
        super(expiresIn);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
