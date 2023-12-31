package com.tcxhb.mizar.admin.utils;

import com.tcxhb.mizar.core.entity.UserDTO;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/3/31
 */
public class ContextHolder {
    private static ThreadLocal<UserDTO> user = new ThreadLocal<>();

    public static UserDTO get() {
        return user.get();
    }

    public static void put(UserDTO userDTO) {
        user.set(userDTO);
    }

    public static void clean() {
        user.remove();
    }
}
