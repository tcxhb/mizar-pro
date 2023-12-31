package com.tcxhb.mizar.core.convert;

import com.tcxhb.mizar.common.model.biz.FlowRule;
import com.tcxhb.mizar.dao.dataobject.FlowRuleDO;
import org.mapstruct.Mapper;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/15
 */
@Mapper(componentModel = "spring")
public interface FlowRuleConverter {
    default FlowRule do2Entity(FlowRuleDO flowRuleDO) {
        FlowRule rule = new FlowRule();
        rule.setAction(flowRuleDO.getFlowAction());
        rule.setResource(flowRuleDO.getResourceCode());
        rule.setType(flowRuleDO.getFlowType());
        rule.setValue(flowRuleDO.getFlowValue());
        return rule;
    }
}
