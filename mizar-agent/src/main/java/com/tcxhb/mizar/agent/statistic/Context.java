package com.tcxhb.mizar.agent.statistic;

import com.tcxhb.mizar.agent.statistic.ResourceWrapper;
import com.tcxhb.mizar.agent.statistic.StatisticNode;
import lombok.Data;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/2
 */
@Data
public class Context {
    private ResourceWrapper resource;
    private StatisticNode node;
    private Long startTime;
    private Throwable error;

}
