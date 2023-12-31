package com.tcxhb.mizar.admin.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * author:auto.generator
 * time: 2023-09-20
 */
@Data
public class UserVO implements Serializable {
    private Long id;
    private String username;
    private String phone;
    private String name;
    private Integer type;
    private String appName;
    private Date createTime;
    private Date updateTime;
    private String email;
}
