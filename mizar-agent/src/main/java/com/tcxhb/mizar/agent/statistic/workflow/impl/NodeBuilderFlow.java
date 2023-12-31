package com.tcxhb.mizar.agent.statistic.workflow.impl;

import com.tcxhb.mizar.agent.statistic.ResourceWrapper;
import com.tcxhb.mizar.agent.statistic.StatisticNode;
import com.tcxhb.mizar.agent.statistic.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/2
 */
public class NodeBuilderFlow extends BaseFlow {
    public static Map<ResourceWrapper, StatisticNode> resourceMap = new ConcurrentHashMap<>();

    @Override
    public void entry(Context context) {
        StatisticNode node = resourceMap.get(context.getResource());
        if (node == null) {
            resourceMap.put(context.getResource(), new StatisticNode());
            node = resourceMap.get(context.getResource());
        }
        //创建初始化节点
        context.setNode(node);
    }
}
