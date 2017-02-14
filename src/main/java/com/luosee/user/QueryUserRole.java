package com.luosee.user;

import com.luosee.business.BusinessDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by server1 on 2016/12/5.
 */
public class QueryUserRole {

    public static String findUserRole(HttpServletRequest request)
    {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) securityContextImpl
                .getAuthentication().getAuthorities();
        String role=null;
        //获取用户权限
        for (GrantedAuthority grantedAuthority : authorities) {
            role=grantedAuthority.getAuthority();
            break;
        }
        return role;
    }

    public static String getRole(Integer role)
    {
        switch (role)
        {
            case 5:return "ROLE_ADMIN";
            case 4:return "ROLE_SELLER";
            case 2:return "ROLE_DECORATION";
            case 0:return "ROLE_USER";
        }
        return "ROLE_USER";
    }

    public static void updateRole(HttpServletRequest request,TbUser user,BusinessDao businessDao)
    {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        securityContextImpl.setAuthentication(UserService.upadteRole(user,businessDao));
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT",securityContextImpl);
    }
}
