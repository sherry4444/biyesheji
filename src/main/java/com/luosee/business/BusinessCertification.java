package com.luosee.business;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by server1 on 2016/11/22.
 */
public class BusinessCertification {
    private BigInteger certificationId;
    private BigInteger businessInfoId;

    @Length(max = 255,message = "公司名最长为255位")
    @NotEmpty
    private String companyName;

    @Length(max = 255,message = "联系人最长为255位")
    @NotEmpty
    private String contactName;

    @Length(max = 255,message = "公司手机最长为255位")
    @NotEmpty
    private String contactMobile;

    @Length(max = 20,message = "公司电话最长为20位")
    @NotEmpty
    private String contactPhone;

    @Length(max = 255,message = "公司网站最长为255位")
    private String website;

    @Length(max = 255,message = "公司邮箱最长为255位")
    @NotEmpty
    private String contactEmail;

    private String licenseImg;

    @Length(max = 20,message = "验证状态最长为20位")
    private String certificationStatus;

    @Length(max = 255,message = "验证原因最长为255位")
    private String certificationCause;

    @NotEmpty
    private String companyAddress;

    @NotEmpty
    private String companyType;

    @NotEmpty
    private String licenseNumber;

    private Date certificationTime;

    private BusinessSubUser businessSubUser;

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setBusinessInfoId(BigInteger businessInfoId) {
        this.businessInfoId = businessInfoId;
    }

    public void setCertificationCause(String certificationCause) {
        this.certificationCause = certificationCause;
    }

    public void setCertificationId(BigInteger certificationId) {
        this.certificationId = certificationId;
    }

    public void setCertificationStatus(String certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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

    public void setLicenseImg(String licenseImg) {
        this.licenseImg = licenseImg;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setCertificationTime(Date certificationTime) {
        this.certificationTime = certificationTime;
    }

    public void setBusinessSubUser(BusinessSubUser businessSubUser) {
        this.businessSubUser = businessSubUser;
    }

    public BigInteger getBusinessInfoId() {
        return businessInfoId;
    }

    public BigInteger getCertificationId() {
        return certificationId;
    }

    public String getCertificationCause() {
        return certificationCause;
    }

    public String getCertificationStatus() {
        return certificationStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContactEmail() {
        return contactEmail;
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

    public String getLicenseImg() {
        return licenseImg;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getCompanyType() {
        return companyType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getWebsite() {
        return website;
    }

    public Date getCertificationTime() {
        return certificationTime;
    }

    public BusinessSubUser getBusinessSubUser() {
        return businessSubUser;
    }
}
