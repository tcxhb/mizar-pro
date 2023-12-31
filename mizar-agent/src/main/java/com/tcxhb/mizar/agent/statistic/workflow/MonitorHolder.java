package com.tcxhb.mizar.agent.statistic.workflow;

import com.tcxhb.mizar.agent.statistic.Context;
import com.tcxhb.mizar.common.constants.BlockException;
import com.tcxhb.mizar.common.model.biz.MetricNode;
import com.tcxhb.mizar.agent.statistic.ResourceWrapper;
import com.tcxhb.mizar.agent.statistic.StatisticNode;
import com.tcxhb.mizar.agent.statistic.workflow.impl.NodeBuilderFlow;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/9
 */
@Slf4j
public class MonitorHolder {
    private static MonitorChain chain = new MonitorChain();

    /**
     * @param resource
     * @return
     */
    public static Context entry(String resource) {
        Context context = new Context();
        try {
            ResourceWrapper wrapper = new ResourceWrapper(resource, 0);
            context.setResource(wrapper);
            chain.entry(context);
        } catch (BlockException e) {
            throw e;
        } catch (Exception e) {
            log.error("entry exp" + resource, e);
            return null;
        }
        return context;
    }

    public static void exit(Context context) {
        try {
            chain.exit(context);
        } catch (Exception e) {
            log.error("exit exp", e);
        }
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<MetricNode> queryMetric(Long startTime, Long endTime) {
        List<MetricNode> result = new LinkedList<>();
        Map<ResourceWrapper, StatisticNode> resource = NodeBuilderFlow.resourceMap;
        for (Map.Entry<ResourceWrapper, StatisticNode> e : resource.entrySet()) {
            StatisticNode node = e.getValue();
            ResourceWrapper wrapper = e.getKey();
            List<MetricNode> metricsList = node.listMetrics(startTime, endTime);
            if (metricsList == null) {
                continue;
            }
            for (MetricNode metric : metricsList) {
                metric.setResource(wrapper.getName());
            }
            result.addAll(metricsList);
        }
        return result;
    }
}
