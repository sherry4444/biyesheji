package com.luosee.business;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Map;

/**
 * Created by server1 on 2016/11/21.
 */
@Service
public class BusinessService {

    @Resource(name = "businessDao")
    private BusinessDao businessDao;

    public void saveBusinessInfo(BusinessCertification businessCertification)
    {
        businessDao.saveBusinessInfo(businessCertification);
    }
    public Integer businessInfoCount()
    {
        return businessDao.businessInfoCount();
    }

    public BigInteger findIdByName(String username){
        return businessDao.findIdByName(username);
    }

    public void saveBusinessCertification(BusinessCertification businessCertification)
    {
        businessDao.saveBusinessCertification(businessCertification);
    }

    public void updateBusinessInfo(BusinessCertification businessCertification)
    {
        businessDao.updateBusinessInfo(businessCertification);
    }

    public void saveBusinessSubUser(BusinessSubUser businessSubUser)
    {
        businessDao.saveBusinessSubUser(businessSubUser);
    }

    public BusinessSubUser findOneBusinessSub(String username)
    {
        return businessDao.findOneBusinessSub(username);
    }

    public BusinessCertification findOneCertification(String username)
    {
        return businessDao.findOneCertification(username);
    }

    public BusinessInfo findBusinessInfoByName(String username)
    {
        return businessDao.findBusinessInfoByName(username);
    }

    public void updateBusinessUserInfo(Map<String,Object> infoMap)
    {
        businessDao.updateBusinessUserInfo(infoMap);
    }

    public BusinessCertification findAttestation(String username)
    {
        return businessDao.findAttestation(username);
    }


    public String findBusinessLogoByName(String username)
    {
        return businessDao.findBusinessLogoByName(username);
    }
    public BusinessCertification findStatus(String username)
    {
        return businessDao.findStatus(username);
    }

}
