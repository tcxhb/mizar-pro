package com.tcxhb.mizar.common.utils;

import java.util.UUID;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/4/3
 */
public class UUidUtils {

    public static String getUid(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }
}
