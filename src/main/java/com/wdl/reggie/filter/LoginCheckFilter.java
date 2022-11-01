package com.wdl.reggie.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:wudl
 * @creat 2022/10/10 21:26
 * @name reggie
 */

@Slf4j
@Deprecated
//@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("过滤器拦截的请求：{}",request.getRequestURI());
//        1、获取本次请求的URI

//        2、判断本次请求是否需要处理
//        3、如果不需要处理，则直接放行
//        4、判断登录状态，如果已登录，则直接放行
        filterChain.doFilter(request,response);
//        5、如果未登录则返回未登录结果

    }
}
