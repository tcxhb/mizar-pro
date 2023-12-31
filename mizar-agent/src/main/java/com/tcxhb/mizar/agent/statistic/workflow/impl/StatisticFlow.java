package com.tcxhb.mizar.agent.statistic.workflow.impl;

import com.tcxhb.mizar.common.utils.TimeUtil;
import com.tcxhb.mizar.agent.statistic.Context;
import com.tcxhb.mizar.common.constants.BlockException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/8
 */
@Slf4j
public class StatisticFlow extends BaseFlow {
    @Override
    public void entry(Context context) {
        if (context.getNode() == null) {
            return;
        }
        context.setStartTime(TimeUtil.currentTimeMillis());
        context.getNode().addPassRequest(1);
    }

    @Override
    public void exit(Context context) {
        if (context.getNode() == null) {
            return;
        }
        Throwable error = context.getError();
        if (error != null) {
            context.getNode().increaseExceptionQps(1);
        } else {
            long endTime = TimeUtil.currentTimeMillis();
            long rt = endTime - context.getStartTime();
            context.getNode().addRtAndSuccess(rt, 1);
        }
    }
}
