package com.tcxhb.mizar.agent.statistic.workflow.impl;

import com.tcxhb.mizar.agent.service.impl.ConfigServiceImpl;
import com.tcxhb.mizar.agent.statistic.Context;
import com.tcxhb.mizar.agent.statistic.ResourceWrapper;
import com.tcxhb.mizar.agent.statistic.StatisticNode;
import com.tcxhb.mizar.common.constants.BlockException;
import com.tcxhb.mizar.common.model.biz.FlowRule;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/15
 */
public class QpsBlockFlow extends BaseFlow {
    @Override
    public void entry(Context context) {
        if (context.getNode() == null) {
            return;
        }
        StatisticNode node = context.getNode();
        ResourceWrapper wrapper = context.getResource();
        List<FlowRule> rules = ConfigServiceImpl.ruleMap.get(wrapper.getName());
        qpsBlock(wrapper, node, rules);
    }

    /**
     * 限流
     *
     * @param wrapper
     * @param node
     * @param list
     */
    public void qpsBlock(ResourceWrapper wrapper, StatisticNode node, List<FlowRule> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 触发限流
        long pass = node.secondPass();
        for (FlowRule rule : list) {
            if (pass < rule.getValue()) {
                continue;
            }
            //限流QPS
            node.increaseBlockQps(1);
            throw new BlockException(wrapper.getName(), pass);
        }
    }
}
