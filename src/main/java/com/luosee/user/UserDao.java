package com.luosee.user;

import com.luosee.pay.UserPay;
import com.luosee.po.Page;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by server1 on 2016/11/15.
 */
public interface UserDao {
    public TbUser findOneByEmail(String username);

    public void save(TbUser account);

    void updataPassword(TbUser user);
    TbUser findOne(Long UserId);

    void updateUserInfo(TbUser user);

    Integer getUserIdByName(String username);

    List<TbUser> findChildUser(Page page);

    Integer findChildCount(String username);

    UserSubAccount findOneChildUser(String username);

    void addChildUser(UserSubAccount userRelation);

    UserPay findUserLastPay(String username);

    void deleteChildAccount(Map<String,Object> childInfo);

    void updateChildPassWord(Map<String,Object> childInfo);

    void updateUserRole(TbUser user);

    String isHasUserName(String username);
    String isHasNickName(TbUser user);

    void updateSubUserInfo(List<TbUser> user);
    TbUser queryUserIdPassword(String username);

    Integer queryUserRole(String username);
    Integer findOneParentId(String username);

    List<Integer> queryChildsUserId(Map<String,Object> childInfo);
    void deleteChild(List<Integer> childInfo);

    String queryParentUserName(BigInteger userId);

    String queryUserGrade(String username);
}
