package com.luosee.user;

import java.util.List;

/**
 * Created by server1 on 2016/12/20.
 */
public class UserItem {
    private List<Integer> childId;

    private List<TbUser> account;

    public void setChildId(List<Integer> childId) {
        this.childId = childId;
    }

    public void setAccount(List<TbUser> account) {
        this.account = account;
    }
    
    public List<Integer> getChildId() {
        return childId;
    }

    public List<TbUser> getAccount() {
        return account;
    }
}
