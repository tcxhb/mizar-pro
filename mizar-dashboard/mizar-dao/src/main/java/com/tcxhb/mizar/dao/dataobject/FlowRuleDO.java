package com.tcxhb.mizar.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author:auto.generator
 * time: 2023-12-14
 */

@TableName(value = "mz_flow_rule", autoResultMap = true)
@Data
public class FlowRuleDO extends BaseDO {


    /**
     * *
     **/
    private String appName;

    /**
     * *
     **/
    private String resourceCode;

    /**
     * 类型QPS
     */
    private String flowType;

    /**
     * 限流操作
     * alarm,limit
     */
    private String flowAction;

    /**
     * *限流阈值
     **/
    private Long flowValue;

    /**
     * *
     **/
    private String feature;
}
