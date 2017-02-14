package com.luosee.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by server1 on 2016/12/1.
 */
@WebFilter(filterName = "VerifyCode",urlPatterns = "/authenticate")
public class VerifyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        HttpSession session=request.getSession();
        String verifyCode= (String) session.getAttribute("verificationCode");
        String loginCode=request.getParameter("verificationCode");
        if(verifyCode!=null&&!verifyCode.equalsIgnoreCase(loginCode) || loginCode == "")
        {
            session.setAttribute("message",1);
            response.sendRedirect("users/login");
        }
        else if (verifyCode!=null&&verifyCode.equalsIgnoreCase(loginCode))
        {
            session.removeAttribute("verificationCode");
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else
        {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
