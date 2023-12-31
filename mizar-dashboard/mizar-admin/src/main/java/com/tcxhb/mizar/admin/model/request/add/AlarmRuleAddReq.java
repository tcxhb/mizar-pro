package com.tcxhb.mizar.admin.model.request.add;

import lombok.Data;

import java.io.Serializable;

/**
 * author:auto.generator
 * time: 2023-12-29
 */
@Data
public class AlarmRuleAddReq implements Serializable {

    private Long id;


    private String resource;


    private String name;
    /**
     * 时间间隔
     */
    private Integer timeInterval;
    /**
     * 出现次数
     */
    private Integer appearLimit;
    /**
     * 阈值
     */
    private Double threshold;


    private String alarmMsg;
    /**
     * 1秒,2分
     */
    private Integer unit;

    private Boolean status;
}
