package com.luosee.business;

import java.math.BigInteger;
import java.util.Map;

/**
 * Created by server1 on 2016/11/21.
 */
public interface BusinessDao {
    void saveBusinessInfo(BusinessCertification businessCertification);
    void saveBusinessSubUser(BusinessSubUser businessSubUser);
    void saveBusinessCertification(BusinessCertification businessCertification);
    void updateBusinessInfo(BusinessCertification businessCertification);
    void updateBusinessUserInfo(Map<String,Object> infoMap);
    BigInteger findIdByName(String username);
    Integer businessInfoCount();
    BusinessSubUser findOneBusinessSub(String username);
    BusinessCertification findOneCertification(String username);
    BusinessInfo findBusinessInfoByName(String username);
    BusinessCertification findAttestation(String username);
    String findBusinessLogoByName(String username);

    BusinessCertification findStatus(String username);

}
