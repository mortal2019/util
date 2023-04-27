package com.mortal2019.weixin.mini_app.dto;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/26 11:43
 */
public class MiniProgramPhoneNumberDto {
    private String purePhoneNumber;
    private String countryCode;

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
