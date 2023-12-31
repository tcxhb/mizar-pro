package com.tcxhb.mizar.agent.service.impl;

import com.tcxhb.mizar.agent.constants.AgentConfig;
import com.tcxhb.mizar.agent.server.thread.ExecutorPool;
import com.tcxhb.mizar.agent.service.ConfigService;
import com.tcxhb.mizar.agent.service.ServerCenterApi;
import com.tcxhb.mizar.common.constants.enums.ActionType;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.biz.FlowRule;
import com.tcxhb.mizar.common.model.biz.FlowRuleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/14
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    private Long currentVersion = -1L;
    @Autowired
    private AgentConfig config;
    @Autowired
    private ServerCenterApi serverCenterApi;
    @Autowired
    private ExecutorPool executorPool;
    public static Map<String, List<FlowRule>> ruleMap = new HashMap<>();

    @Override
    public void refresh(Long version) {
        //强制更新
        if (currentVersion.equals(version)) {
            return;
        }
        //启动线程更新
        executorPool.submit(() -> forceUpdate(config.getAppName()));
    }


    /**
     * 强制更新
     *
     * @param appName
     */
    private void forceUpdate(String appName) {
        MiscResult<FlowRuleConfig> result = serverCenterApi.queryFlowRules(appName);
        if (!result.isSuccess()) {
            return;
        }
        //转换map
        FlowRuleConfig config = result.getData();
        Map<String, List<FlowRule>> map = convertRuleMap(config.getRuleList());
        //强制更新
        synchronized (ruleMap) {
            ruleMap = map;
            this.currentVersion = config.getVersion();
        }
    }

    private Map<String, List<FlowRule>> convertRuleMap(List<FlowRule> rules) {
        Map<String, List<FlowRule>> map = new HashMap<>();
        if (CollectionUtils.isEmpty(rules)) {
            return map;
        }
        //规则
        for (FlowRule rule : rules) {
            if (!ActionType.BLOCK.eq(rule.getAction())) {
                continue;
            }
            List<FlowRule> list = map.get(rule.getResource());
            if (list == null) {
                list = new LinkedList<>();
                map.put(rule.getResource(), list);
            }
            list.add(rule);
        }
        return map;
    }

    @Override
    public Long currentVersion() {
        return currentVersion;
    }

    @Override
    public Map<String, List<FlowRule>> queryRule() {
        return this.ruleMap;
    }
}
