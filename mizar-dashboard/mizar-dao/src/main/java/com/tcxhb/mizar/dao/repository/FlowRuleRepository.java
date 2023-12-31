package com.tcxhb.mizar.dao.repository;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.dao.dataobject.FlowRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.FlowRuleQuery;

import java.util.List;
/**
* author:auto.generator
* time: 2023-12-14
*/
public interface FlowRuleRepository {

    /**
    * 创建对象
    * @param flowRuleDO
    * @return
    */
    Long create(FlowRuleDO flowRuleDO);

    /**
    * 根据ID删除
    * @param id
    * @return
    */
    boolean deleteById(Long id);

    /**
    * 根据ID更新
    * @param flowRuleDO
    * @return
    */
    boolean updateById(FlowRuleDO flowRuleDO);

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    FlowRuleDO queryById(Long id);

    /**
    * 分页查询
    * @param query
    * @return
    */
    PageResponse<FlowRuleDO> page(FlowRuleQuery query);

    /**
    * 列表查询
    * @param query
    * @return
    */
    List<FlowRuleDO> list(FlowRuleQuery query);
}
