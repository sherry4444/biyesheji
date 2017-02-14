package com.luosee.business;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigInteger;

/**
 * Created by server1 on 2016/11/21.
 */
public class Seller {
    private BigInteger sellerId;

    @Length(max = 50,message = "品牌名最长为50位")
    @NotEmpty
    private String sellerName;

    private String sellerLogo;
    private BigInteger businessInfoId;
    private Boolean visual;
    private String certificationStatus;
    private String powerAttorney;
    private String trademark;
    private BusinessSubUser businessSubUser;

    public void setPowerAttorney(String powerAttorney) {
        this.powerAttorney = powerAttorney;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public void setCertificationStatus(String certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public void setBusinessSubUser(BusinessSubUser businessSubUser) {
        this.businessSubUser = businessSubUser;
    }

    public void setSellerId(BigInteger sellerId) {
        this.sellerId = sellerId;
    }

    public void setSellerLogo(String sellerLogo) {
        this.sellerLogo = sellerLogo;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setBusinessInfoId(BigInteger businessInfoId) {
        this.businessInfoId = businessInfoId;
    }

    public void setVisual(Boolean visual) {
        this.visual = visual;
    }

    public BigInteger getSellerId() {
        return sellerId;
    }

    public String getSellerLogo() {
        return sellerLogo;
    }

    public String getSellerName() {
        return sellerName;
    }

    public BigInteger getBusinessInfoId() {
        return businessInfoId;
    }

    public Boolean getVisual() {
        return visual;
    }

    public BusinessSubUser getBusinessSubUser() {
        return businessSubUser;
    }

    public String getCertificationStatus() {
        return certificationStatus;
    }

    public String getPowerAttorney() {
        return powerAttorney;
    }

    public String getTrademark() {
        return trademark;
    }
}
