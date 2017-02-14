package com.luosee.user;

import java.util.Date;

/**
 * Created by server1 on 2016/11/16.
 */
public class MobileVerify {
    private String mobile;

    private String verifyCode;

    private String verifyType;

    private Date createTime;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public String getVerifyType() {
        return verifyType;
    }


}
