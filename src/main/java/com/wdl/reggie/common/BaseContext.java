package com.wdl.reggie.common;

/**
 * @Author:wudl
 * @creat 2022/10/14 10:40
 * @name reggie
 */
public class BaseContext {

    private static final ThreadLocal<Long>  THREAD_LOCAL_USERID = new ThreadLocal<>();

    public static Long getUserid() {
        return THREAD_LOCAL_USERID.get();
    }

    public static void setUserid(Long currentUserid) {
        THREAD_LOCAL_USERID.set(currentUserid);
    }
}
