package com.tcxhb.mizar.agent.service;

import com.tcxhb.mizar.common.model.biz.FlowRule;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/14
 */
public interface ConfigService {
    void refresh(Long versionID);

    Long currentVersion();

    Map<String, List<FlowRule>> queryRule();
}
