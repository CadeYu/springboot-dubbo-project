package com.stylefeng.guns.rest.common;

public class CurrentUser {

    //和线程相关的存储空间，每一个线程都不一样
    private static final ThreadLocal<String> threadLocal =
            new ThreadLocal<>();

    public static void save(String userId){
        threadLocal.set(userId);
    }

    public static String get(){
        return threadLocal.get();
    }


}
