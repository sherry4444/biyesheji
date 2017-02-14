package com.luosee.user;

import com.luosee.business.*;
import com.luosee.common.SaltedUser;
import com.luosee.oss.UploadImage;
import com.luosee.pay.UserPay;
import com.luosee.plan.Plan;
import com.luosee.plan.PlanDao;
import com.luosee.po.Page;
import com.luosee.product.ProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by server1 on 2016/11/15.
 */
@Service
public class UserService implements UserDetailsService {
    private UserDao userDao;

    private BusinessDao businessDao;

    @Resource(name = "businessModelDao")
    private BusinessModelDao businessModelDao;

    @Resource(name = "sellerDao")
    private SellerDao sellerDao;

    @Resource(name = "planDao")
    private PlanDao planDao;

    @Resource(name = "productDao")
    private ProductDao productDao;

    final Logger logger= LoggerFactory.getLogger(UserService.class);
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public BusinessDao getBusinessDao() {
        return businessDao;
    }

    public void setBusinessDao(BusinessDao businessDao) {
        this.businessDao = businessDao;
    }

    public void setBusinessModelDao(BusinessModelDao businessModelDao) {
        this.businessModelDao = businessModelDao;
    }

    public void setPlanDao(PlanDao planDao) {
        this.planDao = planDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void setSellerDao(SellerDao sellerDao) {
        this.sellerDao = sellerDao;
    }

    public BusinessModelDao getBusinessModelDao() {
        return businessModelDao;
    }

    public PlanDao getPlanDao() {
        return planDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public SellerDao getSellerDao() {
        return sellerDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TbUser user = userDao.findOneByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(user,businessDao);
    }

    public void signin(TbUser user,BusinessDao businessDao) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user,businessDao));
    }

    public static Authentication upadteRole(TbUser user,BusinessDao businessDao)
    {
        UserService userService=new UserService();

        return userService.authenticate(user,businessDao);
    }

    //
    private Authentication authenticate(TbUser user,BusinessDao businessDao) {
        return new UsernamePasswordAuthenticationToken(createUser(user,businessDao), null, Collections.singleton(createAuthority(user)));
    }

    private SaltedUser createUser(TbUser user,BusinessDao businessDao) {
        BusinessCertification businessCertification = null;
        if(!"3".equals(user.getGrade())) {
            businessCertification = businessDao.findStatus(user.getUsername());
        }
        
        if(businessCertification !=null) {
            return new SaltedUser(user.getUsername(), user.getPassword(), true, true, true, true, Collections.singleton(createAuthority(user)), user.getSalt(), user.getNickname(), user.getGrade(), businessCertification.getCertificationStatus(),user.getUserId());
        }
        return new SaltedUser(user.getUsername(), user.getPassword(), true, true, true, true, Collections.singleton(createAuthority(user)), user.getSalt(), user.getNickname(), user.getGrade(), null,user.getUserId());
    }

    private GrantedAuthority createAuthority(TbUser user) {
        return new SimpleGrantedAuthority(QueryUserRole.getRole(user.getRole()));
    }

    public void updataPassword(TbUser user)
    {
        userDao.updataPassword(user);
    }

    public TbUser qualifyUserInfo(String username)
    {
        return userDao.findOneByEmail(username);
    }

    public void updateUserInfo(TbUser user)
    {
        userDao.updateUserInfo(user);
    }

    public List<TbUser> findChildUser(Page page)
    {
        return userDao.findChildUser(page);
    }

    public Integer findChildCount(String username)
    {
        return userDao.findChildCount(username);
    }

    public UserSubAccount findOneChildUser(String username)
    {
        return userDao.findOneChildUser(username);
    }

    public void addChildUser(UserSubAccount userRelation)
    {
        userDao.addChildUser(userRelation);
    }

    public UserPay findUserLastPay(String username)
    {
        return userDao.findUserLastPay(username);
    }

    public void deleteChildAccount(Map<String,Object> childInfo,String datasource)
    {

        List<Integer> ids=userDao.queryChildsUserId(childInfo);
        List<String> floorPlanUrl=planDao.queryFloorPlanUrl(ids);
        List<Plan> plan=planDao.queryPlanResources(ids);

        int i,r;
        UploadImage image=new UploadImage();

        for(i=0;floorPlanUrl!=null && i<floorPlanUrl.size();i++)
        {
            image.deleteFile(datasource,floorPlanUrl.get(i));
        }

        for(i=0;plan!=null && i<plan.size();i++)
        {
            image.deleteFile(datasource,plan.get(i).getImagePath());
            image.deleteFile(datasource,plan.get(i).getDownloadU3DUrl());
        }

        planDao.deleteChildTag(ids);
        planDao.deleteChildPlan(ids);
        userDao.deleteChildAccount(childInfo);
        userDao.deleteChild(ids);
    }

    public void updateChildPassWord(Map<String,Object> childInfo)
    {
        userDao.updateChildPassWord(childInfo);
    }

    public void updateUserRole(TbUser user)
    {
        userDao.updateUserRole(user);
    }

    public boolean isHasUserName(String username)
    {
        if(userDao.isHasUserName(username) != null)
        {
            return  true;
        }
        return false;
    }

    public void updateSubUserInfo(List<TbUser> user)
    {
        userDao.updateSubUserInfo(user);
    }

    public TbUser queryUserIdPassword(String username)
    {
        return userDao.queryUserIdPassword(username);
    }
}
