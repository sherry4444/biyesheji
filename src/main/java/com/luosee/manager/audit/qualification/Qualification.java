package com.luosee.manager.audit.qualification;

import java.sql.Timestamp;

/**
 * Created by server2 on 2016/11/15.
 * tb_bussiness_certification
 */
public class Qualification {


    private Integer certificationId;//认证ID
    private Integer businessInfoId;//商家id
    private String companyName;//公司名字
    private String contactName;//联系人姓名
    private String contactMobile;//固话
    private String contactPhone;//手机
    private String website;//公司网址
    private String contactEmail;//公司邮箱
    private String companyAddress;//公司地址
    private String companyType;//营业类型
    private String licenseNumber;//营业号码
    private String licenseImg;//营业执照图
    private String certificationStatus;//认证状态
    private String certificationCause;//认证原因
    private Timestamp certificationTime;//认证时间

    public Qualification(){

    }

    public Qualification(Integer certificationId, Integer businessInfoId, String companyName, String contactName, String contactMobile, String contactPhone, String website, String contactEmail, String companyAddress, String companyType, String licenseNumber, String licenseImg, String certificationStatus, String certificationCause, Timestamp certificationTime) {
        this.certificationId = certificationId;
        this.businessInfoId = businessInfoId;
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactMobile = contactMobile;
        this.contactPhone = contactPhone;
        this.website = website;
        this.contactEmail = contactEmail;
        this.companyAddress = companyAddress;
        this.companyType = companyType;
        this.licenseNumber = licenseNumber;
        this.licenseImg = licenseImg;
        this.certificationStatus = certificationStatus;
        this.certificationCause = certificationCause;
        this.certificationTime = certificationTime;
    }

    public Integer getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Integer certificationId) {
        this.certificationId = certificationId;
    }

    public Integer getBusinessInfoId() {
        return businessInfoId;
    }

    public void setBusinessInfoId(Integer businessInfoId) {
        this.businessInfoId = businessInfoId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseImg() {
        return licenseImg;
    }

    public void setLicenseImg(String licenseImg) {
        this.licenseImg = licenseImg;
    }

    public String getCertificationStatus() {
        return certificationStatus;
    }

    public void setCertificationStatus(String certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public String getCertificationCause() {
        return certificationCause;
    }

    public void setCertificationCause(String certificationCause) {
        this.certificationCause = certificationCause;
    }

    public Timestamp getCertificationTime() {
        return certificationTime;
    }

    public void setCertificationTime(Timestamp certificationTime) {
        this.certificationTime = certificationTime;
    }
}
