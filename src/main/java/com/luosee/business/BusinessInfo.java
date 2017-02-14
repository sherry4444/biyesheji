package com.luosee.business;

/**
 * Created by server1 on 2016/11/21.
 */
public class BusinessInfo {

    private String comanyName;

    private String contactName;

    private String contactMobile;

    private String contactPhone;

    private Float balance;

    private Integer businessInfoId;

    private String province;

    private String city;

    private String logo;

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public void setBusinessInfoId(Integer businessInfoId) {
        this.businessInfoId = businessInfoId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setComanyName(String comanyName) {
        this.comanyName = comanyName;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Float getBalance() {
        return balance;
    }

    public Integer getBusinessInfoId() {
        return businessInfoId;
    }

    public String getCity() {
        return city;
    }

    public String getComanyName() {
        return comanyName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getLogo() {
        return logo;
    }

    public String getProvince() {
        return province;
    }
}
