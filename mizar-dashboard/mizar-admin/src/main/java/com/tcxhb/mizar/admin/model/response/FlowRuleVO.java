package com.tcxhb.mizar.admin.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * author:auto.generator
 * time: 2023-12-14
 */
@Data
public class FlowRuleVO implements Serializable {

    private Integer id;

    private String appName;

    private String resourceCode;

    private String flowType;

    private String flowAction;

    private String flowValue;

    private String value;

    private Date updateTime;
}
