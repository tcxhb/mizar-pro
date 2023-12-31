package com.tcxhb.mizar.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author:auto.generator
 * time: 2023-09-20
 */

@TableName(value = "mz_user", autoResultMap = true)
@Data
public class MiscUserDO extends BaseDO {
    /**
     * *登录用户名，唯一
     **/
    private String username;

    /**
     * *密码
     **/
    private String password;

    /**
     * *手机号码，唯一
     **/
    private String phone;
    /**
     * *姓名
     **/
    private String name;

    /**
     * *1: 超级管理源，2：其它管理员
     **/
    private Integer type;
    /**
     * 应用名称
     */
    private String appName;

    private String email;
}
