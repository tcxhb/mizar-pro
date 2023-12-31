package com.tcxhb.mizar.agent.metric;

/**
 * 度量的事件类型
 *
 * @author wujiuye
 */
public enum MetricEvent {
    Block,
    PASS,
    /**
     * 异常
     */
    EXCEPTION,
    /**
     * 成功
     */
    SUCCESS,
    /**
     * 耗时
     */
    RT

}
