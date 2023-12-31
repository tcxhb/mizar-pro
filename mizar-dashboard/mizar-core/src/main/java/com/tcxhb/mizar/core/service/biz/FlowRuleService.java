package com.tcxhb.mizar.core.service.biz;

import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.model.biz.FlowRuleConfig;
import com.tcxhb.mizar.dao.dataobject.FlowRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.FlowRuleQuery;

/**
 * author:auto.generator
 * time: 2023-12-14
 */
public interface FlowRuleService {
    /**
     * 创建
     *
     * @param flowRuleDO
     * @return
     */
    Long create(FlowRuleDO flowRuleDO);

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 更新
     *
     * @param flowRuleDO
     * @return
     */
    boolean updateById(FlowRuleDO flowRuleDO);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    FlowRuleDO queryById(Long id);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageResponse<FlowRuleDO> page(FlowRuleQuery query);

    /**
     * 列表查询
     *
     * @return
     */
    FlowRuleConfig queryAgentRules(String appName);

}
