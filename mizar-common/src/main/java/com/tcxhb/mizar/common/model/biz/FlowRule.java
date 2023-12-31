package com.tcxhb.mizar.common.model.biz;

import lombok.Data;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/14
 */
@Data
public class FlowRule {
    /**
     * 资源名称
     */
    private String resource;
    /**
     * 类型QPS
     * 限流类型
     */
    private String type;
    /**
     * 操作:alarm,limit
     */
    private String action;
    /**
     * 限流阈值
     *
     */
    private Long value;
}
