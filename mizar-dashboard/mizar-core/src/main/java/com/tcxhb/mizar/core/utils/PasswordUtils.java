package com.tcxhb.mizar.core.utils;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/20
 */
public class PasswordUtils {

    public static String generatePassword(String username,String md5pwd){
        //生成md5
        String str = username+md5pwd;
        return Md5Utils.getMD5(str);
    }

    public static void main(String []arg){
        String str = Md5Utils.getMD5("123456");
        str = generatePassword("admin",str);
        System.out.println(str);
    }
}
