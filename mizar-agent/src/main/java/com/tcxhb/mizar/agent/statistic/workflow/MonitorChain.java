package com.tcxhb.mizar.agent.statistic.workflow;

import com.tcxhb.mizar.agent.statistic.Context;
import com.tcxhb.mizar.agent.statistic.workflow.impl.NodeBuilderFlow;
import com.tcxhb.mizar.agent.statistic.workflow.impl.QpsBlockFlow;
import com.tcxhb.mizar.agent.statistic.workflow.impl.StatisticFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/3
 */
public class MonitorChain {
    private static List<Flow> chain = new ArrayList<Flow>();

    static {
        //监控节点处理流程
        chain.add(new NodeBuilderFlow());
        //qsp限流处理流程
        chain.add(new QpsBlockFlow());
        //qps统计处理流程
        chain.add(new StatisticFlow());
    }

    /**
     * @param context
     */
    public void entry(Context context) {
        for (Flow flow : chain) {
            flow.entry(context);
        }
    }

    public void exit(Context context) {
        for (Flow flow : chain) {
            flow.exit(context);
        }
    }
}
