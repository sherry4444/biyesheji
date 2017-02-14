package com.luosee.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by server1 on 2016/12/8.
 */
public class AcountLogin {
    static final Logger logger= LoggerFactory.getLogger(AcountLogin.class);

    public static String loginType(String role)
    {
        String page="redirect:/qualifyUserInfo";
        switch (role)
        {
            case "ROLE_SELLER":page="home/homeSignedIn";break;
            case "ROLE_USER":page="home/homeSignedIn";break;
            case "ROLE_DECORATION":page="home/homeSignedIn";break;
            case "ROLE_ADMIN":page="manager/home";break;
        }
        return page;
    }
}
