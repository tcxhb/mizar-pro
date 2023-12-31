package com.tcxhb.mizar.common.model.biz;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/15
 */
@Data
public class FlowRuleConfig {
    private Long version = 0L;
    List<FlowRule> ruleList;
}
