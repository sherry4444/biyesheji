package com.luosee.business;

import java.math.BigInteger;

/**
 * Created by server1 on 2016/11/21.
 */
public class BusinessSubUser {
    private BigInteger subUserId;

    private String subUsername;

    private String subPassword;

    private String deviceId;

    private Integer businessUserID;

    private String salt;

    public void setBusinessUserID(Integer businessUserID) {
        this.businessUserID = businessUserID;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setSubPassword(String subPassword) {
        this.subPassword = subPassword;
    }

    public void setSubUserId(BigInteger subUserId) {
        this.subUserId = subUserId;
    }

    public void setSubUsername(String subUsername) {
        this.subUsername = subUsername;
    }

    public BigInteger getSubUserId() {
        return subUserId;
    }

    public Integer getBusinessUserID() {
        return businessUserID;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getSalt() {
        return salt;
    }

    public String getSubPassword() {
        return subPassword;
    }

    public String getSubUsername() {
        return subUsername;
    }
}
