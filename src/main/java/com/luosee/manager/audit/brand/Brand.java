package com.luosee.manager.audit.brand;

import java.sql.Timestamp;

/**
 * Created by server2 on 2016/12/14.
 */

//tb_seller
public class Brand {
    private Integer sellerId;//品牌ID
    private String sellerName;//品牌名字
    private String sellerLogo;//品牌LOGO
    private Integer businessInfoId;//品牌对应的商家ID
    private Integer visual;//默认为0（可见），1（不可见）
    private Timestamp submitTime;//提交时间
    private String certificationStatus;// 默认值（认证中），认证成功，认证失败
    private String certificationCause;// 默认值（认证中），认证成功，认证失败
    private Timestamp certificationTime;//审核认证时间

    public Brand(){}

    public Brand(Integer sellerId, String sellerName, String sellerLogo, Integer businessInfoId, Integer visual, Timestamp submitTime, String certificationStatus, String certificationCause, Timestamp certificationTime) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.sellerLogo = sellerLogo;
        this.businessInfoId = businessInfoId;
        this.visual = visual;
        this.submitTime = submitTime;
        this.certificationStatus = certificationStatus;
        this.certificationCause = certificationCause;
        this.certificationTime = certificationTime;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerLogo() {
        return sellerLogo;
    }

    public void setSellerLogo(String sellerLogo) {
        this.sellerLogo = sellerLogo;
    }

    public Integer getBusinessInfoId() {
        return businessInfoId;
    }

    public void setBusinessInfoId(Integer businessInfoId) {
        this.businessInfoId = businessInfoId;
    }

    public Integer getVisual() {
        return visual;
    }

    public void setVisual(Integer visual) {
        this.visual = visual;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
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
