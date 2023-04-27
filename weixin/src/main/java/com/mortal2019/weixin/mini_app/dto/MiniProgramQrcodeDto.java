package com.mortal2019.weixin.mini_app.dto;

import java.io.InputStream;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/26 11:45
 */
public class MiniProgramQrcodeDto {
    private final InputStream inputStream;

    public MiniProgramQrcodeDto(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
