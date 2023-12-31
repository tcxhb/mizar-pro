package com.tcxhb.mizar.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

/**
 * author:auto.generator
 * time: 2023-12-29
 */

@TableName(value = "mz_alarm_rule", autoResultMap = true)
@Data
public class AlarmRuleDO extends BaseDO {

    /**
     * 资源ID
     **/
    private String resource;

    /**
     * *
     **/
    private String name;

    /**
     * *时间间隔毫秒
     **/
    private Integer timeInterval;

    /**
     * *出现次数
     **/
    private Integer appearLimit;

    /**
     * *阈值
     **/
    private Double threshold;

    /**
     * * 告警文案
     **/
    private String alarmMsg;

    /**
     * 1秒,2分
     */
    private Integer unit;

    private Boolean status;

}
