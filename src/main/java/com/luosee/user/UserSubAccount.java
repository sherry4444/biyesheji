package com.luosee.user;

import java.math.BigInteger;

/**
 * Created by server1 on 2016/12/16.
 */
public class UserSubAccount {
    private int childId;
    private BigInteger childUserId;
    private BigInteger parentUserId;
    private String remarks;
    private TbUser user;

    public void setUser(TbUser user) {
        this.user = user;
    }

    public void setChildUserId(BigInteger childUserId) {
        this.childUserId = childUserId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setParentUserId(BigInteger parentUserId) {
        this.parentUserId = parentUserId;
    }

    public int getChildId() {
        return childId;
    }

    public BigInteger getChildUserId() {
        return childUserId;
    }

    public String getRemarks() {
        return remarks;
    }

    public BigInteger getParentUserId() {
        return parentUserId;
    }

    public TbUser getUser() {
        return user;
    }
}
