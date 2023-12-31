package com.tcxhb.mizar.agent.statistic;

import com.tcxhb.mizar.common.model.biz.MetricNode;

import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/8
 */
public interface Node {

    /**
     * Add pass count.
     *
     * @param count count to add pass
     */
    void addPassRequest(int count);

    /**
     * Add rt and success count.
     *
     * @param rt      response time
     * @param success success count to add
     */
    void addRtAndSuccess(long rt, int success);

    /**
     * Increase the block count.
     *
     * @param count count to add
     */
    void increaseBlockQps(int count);

    /**
     * Add the biz exception count.
     *
     * @param count count to add
     */
    void increaseExceptionQps(int count);

    /**
     *
     */
    void reset();

    /**
     * 每秒通过
     * @return
     */
    long secondPass();
    /**
     *
     * @param startTime 毫秒
     * @param endTime   毫秒
     * @return
     */
    List<MetricNode> listMetrics(Long startTime,Long endTime);
}
