package com.tcxhb.mizar.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

/**
 * author:auto.generator
 * time: 2023-12-20
 */

@TableName(value = "mz_metrics", autoResultMap = true)
@Data
public class MetricsDO {

    /**
     * * 主键ID
     **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * *应用名称
     **/
    private String app;

    /**
     * *资源名
     **/
    private String resource;

    /**
     * *监控信息的时间戳
     **/
    private Date timestamp;

    /**
     * *通过QPS
     **/
    private Long passQps;

    /**
     * *成功QPS
     **/
    private Long successQps;

    /**
     * *拒绝QPS
     **/
    private Long blockQps;

    /**
     * *异常QPS
     **/
    private Long exceptionQps;

    /**
     * *所有successQps的rt的和
     **/
    private Double rt;

    /**
     * *本次聚合的总条数
     **/
    private Integer count;

    /**
     * *创建时间
     **/
    private Date gmtCreate;

}
