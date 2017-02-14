package com.luosee.pay;

import java.sql.Date;

/**
 * Created by server1 on 2017/2/6.
 */
public class Subscribe {
    private Integer paymentId;
    private Date validityDate;
    private Integer payType;
    private Integer userId;

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public Integer getPayType() {
        return payType;
    }

    public Integer getUserId() {
        return userId;
    }
}
