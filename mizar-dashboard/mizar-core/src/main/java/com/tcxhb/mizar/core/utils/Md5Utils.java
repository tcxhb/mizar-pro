package com.tcxhb.mizar.core.utils;

import org.springframework.util.DigestUtils;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/11
 */
public class Md5Utils {

    public static String getMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}
