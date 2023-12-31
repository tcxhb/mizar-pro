package com.tcxhb.mizar.agent.statistic;

import com.tcxhb.mizar.agent.metric.ArrayMetric;
import com.tcxhb.mizar.agent.metric.Metric;
import com.tcxhb.mizar.common.model.biz.MetricNode;

import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/3
 */
public class StatisticNode implements Node {
    private transient Metric rollingCounterInMinute;
    private transient volatile Metric rollingCounterInSecond;
    public static final int SAMPLE_COUNT = 2;
    public static final int WINDOW_INTERVAL_MS = 1000;

    public StatisticNode() {
        /**
         * 保存最近60秒的统计信息。
         * windowLengthInMs设置为1000毫秒，这意味着每一个bucket对应每秒
         */
        rollingCounterInMinute = new ArrayMetric(60, 60 * 1000);
        rollingCounterInSecond = new ArrayMetric(SAMPLE_COUNT, WINDOW_INTERVAL_MS);
    }

    @Override
    public void addPassRequest(int count) {
        rollingCounterInSecond.addPass(count);
        rollingCounterInMinute.addPass(count);
    }

    @Override
    public void addRtAndSuccess(long rt, int successCount) {
        //RT
        rollingCounterInMinute.addSuccess(successCount);
        rollingCounterInMinute.addRt(rt);
    }

    @Override
    public void increaseBlockQps(int count) {
        rollingCounterInMinute.addBlock(count);
    }

    @Override
    public void increaseExceptionQps(int count) {
        rollingCounterInMinute.addException(count);
    }

    @Override
    public void reset() {
        rollingCounterInSecond = new ArrayMetric(SAMPLE_COUNT, WINDOW_INTERVAL_MS);
    }

    @Override
    public long secondPass() {
        return rollingCounterInSecond.pass();
    }

    @Override
    public List<MetricNode> listMetrics(Long startTime, Long endTime) {
        return rollingCounterInMinute.copyWindows(startTime, endTime);
    }
}
