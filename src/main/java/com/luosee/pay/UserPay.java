package com.luosee.pay;

import com.luosee.user.TbUser;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by server1 on 2016/12/19.
 */
public class UserPay {
    private Integer payId;
    private Integer userId;
    private Integer payType;
    private Date payDate;
    private BigDecimal money;
    private String tradeNo;
    private String tradeStatus;
    private String outTradeNo;
    private Date payCreateDate;
    private TbUser user;

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public void setPayCreateDate(Date payCreateDate) {
        this.payCreateDate = payCreateDate;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public void setUser(TbUser user) {
        this.user = user;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getUserId() {
        return userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public Date getPayDate() {
        return payDate;
    }


    public TbUser getUser() {
        return user;
    }

    public Integer getPayId() {
        return payId;
    }

    public Integer getPayType() {
        return payType;
    }

    public Date getPayCreateDate() {
        return payCreateDate;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }
}
