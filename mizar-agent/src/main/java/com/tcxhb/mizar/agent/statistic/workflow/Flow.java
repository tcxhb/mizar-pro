package com.tcxhb.mizar.agent.statistic.workflow;

import com.tcxhb.mizar.agent.statistic.Context;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/2
 */
public interface Flow {
    /**
     * 入
     *
     * @param context
     */
    void entry(Context context);

    /**
     * 出
     *
     * @param context
     */
    void exit(Context context);
}
