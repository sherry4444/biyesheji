package com.luosee.common;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by server1 on 2016/11/16.
 */
public class AccessKeyInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) {
        String referer = request.getHeader("Referer");
        String host = request.getRemoteHost();
        //手机端访问时间referer为空
        if (referer == null) {
            response.setHeader("Access-Control-Allow-Origin", "");
            return true;
        } else {
            response.setHeader("Access-Control-Allow-Origin", referer.substring(0, 21));
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            response.setHeader("Allow", "POST");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {
        String host = request.getRemoteHost();
    }
}
