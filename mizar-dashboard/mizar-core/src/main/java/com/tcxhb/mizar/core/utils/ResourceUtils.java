package com.tcxhb.mizar.core.utils;


import java.text.DecimalFormat;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/1/7
 */
public class ResourceUtils {
    /**
     * 构建key
     *
     * @param app
     * @param resource
     * @return
     */
    public static String getResourceKey(String app, String resource) {
        StringBuilder builder = new StringBuilder();
        builder.append(app).append("/").append(resource);
        return builder.toString();
    }
}
