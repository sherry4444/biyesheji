package com.luosee.pay;

import java.util.List;

/**
 * Created by server1 on 2017/1/7.
 */
public interface PayDao {
    void createTransaction(UserPay userPay);
    void updateUserPayInfo(UserPay userPay);
    void createPayment(Subscribe payment);
    void updateUserPayment(Subscribe payment);
    Subscribe hasPayment(Integer userId);
    List<Subscribe> userPackage(String username);
}
