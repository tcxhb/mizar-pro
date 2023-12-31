package com.tcxhb.mizar.core.service.alarm;

import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.dao.dataobject.AlarmRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.AlarmRuleQuery;

import java.util.List;

/**
 * author:auto.generator
 * time: 2023-12-29
 */
public interface AlarmRuleService {
    /**
     * 创建
     *
     * @param alarmRuleDO
     * @return
     */
    Long create(AlarmRuleDO alarmRuleDO);

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
     * @param alarmRuleDO
     * @return
     */
    boolean updateById(AlarmRuleDO alarmRuleDO);


    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageResponse<AlarmRuleDO> page(AlarmRuleQuery query);

    /**
     * 列表查询
     *
     * @param query
     * @return
     */
    List<AlarmRuleDO> list(AlarmRuleQuery query);

    /**
     * 返回所有的在线告警规则
     * @return
     */
    List<AlarmRuleDO> onlineCacheRule();

    /**
     * 根据资源查找
     * @param resource
     * @return
     */
    AlarmRuleDO queryRuleByCode(String resource);
}
