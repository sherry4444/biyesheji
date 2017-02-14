package com.luosee.dao;

import com.luosee.account.Account;

/**
 * Created by server1 on 2016/11/10.
 */
public interface AccountRepository {
    public Account findOneByEmail(String username);

    public void save(Account account);

    Account findOne(Long id);
}
