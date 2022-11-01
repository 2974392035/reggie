package com.wdl.reggie.interceptor;

import com.alibaba.fastjson.JSON;
import com.wdl.reggie.common.BaseContext;
import com.wdl.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:wudl
 * @creat 2022/10/10 21:46
 * @name reggie
 */

@Slf4j
//@Deprecated
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long loginId = (Long) request.getSession().getAttribute("employee");
        log.info("拦截器 拦截地址：{}", request.getRequestURI());
        if (!ObjectUtils.isEmpty(loginId)) {
            //放行
            log.info("拦截器 放行");
            //把当前id保存在 ThreadLocal 中
            BaseContext.setUserid(loginId);

            return true;
        }
        //拦截
//        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

//        request.getRequestDispatcher("/backend/page/login/login.html").forward(request,response);
        log.info("拦截器 拦截");
        response.sendRedirect("/backend/page/login/login.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
