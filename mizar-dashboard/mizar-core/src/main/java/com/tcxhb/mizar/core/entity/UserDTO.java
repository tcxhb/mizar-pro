package com.tcxhb.mizar.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/3/31
 */
@Data
public class UserDTO implements Serializable {
    private String username;

    private Long userId;
    /*
     *type = 1表示超级系统管理员
     */
    private Integer type;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * sessionID
     */
    private String token;
}
