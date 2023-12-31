package com.tcxhb.mizar.admin.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * author:auto.generator
 * time: 2023-11-05
 */
@Data
public class MachineVO implements Serializable {
    private Integer id;
    private String appName;
    private String ip;
    private Integer port;
    private Integer status;
    /**
     * 心跳时间
     */
    private Date beatTime;
    private Date updateTime;
    private Date createTime;
}
