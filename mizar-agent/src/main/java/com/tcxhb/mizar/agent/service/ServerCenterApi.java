package com.tcxhb.mizar.agent.service;


import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.biz.FlowRuleConfig;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
public interface ServerCenterApi {
    /**
     * 注册
     *
     * @param ip
     * @param port
     * @return 会返回版本号
     */
    MiscResult<Long> register(String ip, Integer port, String appname);

    /**
     * 注册
     *
     * @param ip
     * @param port
     * @return
     */
    MiscResult<Boolean> offline(String ip, Integer port, String appname);
    /**
     * 请求规则
     * @param appname
     * @return
     */
    MiscResult<FlowRuleConfig> queryFlowRules(String appname);

}

