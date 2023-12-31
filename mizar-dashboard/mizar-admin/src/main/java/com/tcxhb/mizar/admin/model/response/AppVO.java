package com.tcxhb.mizar.admin.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * author:auto.generator
 * time: 2023-11-02
 */
@Data
public class AppVO implements Serializable {

    private Long id;
    private String appName;
    private String name;
    private Date createTime;
    private Date updateTime;
    /**
     * 密钥
     */
    private String appsecret;
}
