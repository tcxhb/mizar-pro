package com.tcxhb.mizar.core.utils;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/21
 */
public class UrlUtils {
    public static String [] splitHttp(String url){
        String str = url.replace("http://", "").replace("/", "");
        return str.split(":");
    }

}
