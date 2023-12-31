package com.tcxhb.mizar.admin.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * author:auto.generator
 * time: 2023-12-29
 */
@Data
public class AlarmRuleVO implements Serializable {

    private Long id;
    private String resource;
    private String name;
    private Integer timeInterval;
    private Integer appearLimit;
    private Double threshold;
    private String alarmMsg;
    private Date createTime;
    private Date updateTime;

    /**
     * 1秒,2分
     */
    private Integer unit;

    private Boolean status;
}
