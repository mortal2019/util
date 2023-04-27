package com.mortal2019.weixin.mini_app.url;

import com.mortal2019.okhttp3.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/26 8:49
 */
public enum MiniProgramUrl {
    // 获取access_token
    AUTH_ACCESS_TOKEN_URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential", OkHttpUtil.MethodModel.GET),

    // 获取openid
    SNS_JSCODE_2_SESSION_URL("https://api.weixin.qq.com/sns/jscode2session", OkHttpUtil.MethodModel.GET),

    // 小程序获取手机号
    BUSINESS_GET_USER_PHONE_NUMBER_URL("https://api.weixin.qq.com/wxa/business/getuserphonenumber", OkHttpUtil.MethodModel.POST),

    // 获取小程序码
    WXA_GET_WXA_CODE_UN_LIMIT_URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit", OkHttpUtil.MethodModel.POST),

    // 获取小程序二维码
    APP_GET_WXA_CODE_UN_LIMIT_URL("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode", OkHttpUtil.MethodModel.POST);

    private final String url;
    private OkHttpUtil.MethodModel methodModel;
    private final Map<String, Object> query;

    MiniProgramUrl(String url, OkHttpUtil.MethodModel methodModel) {
        this.url = url;
        this.methodModel = methodModel;
        this.query = new HashMap<>();
    }

    public MiniProgramUrl addQuery(String key, Object value) {
        this.query.put(key, value);
        return this;
    }

}
