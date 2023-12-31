package com.tcxhb.mizar.dao.repository;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.dao.dataobject.AlarmRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.AlarmRuleQuery;

import java.util.List;
/**
* author:auto.generator
* time: 2023-12-29
*/
public interface AlarmRuleRepository {

    /**
    * 创建对象
    * @param alarmRuleDO
    * @return
    */
    Long create(AlarmRuleDO alarmRuleDO);

    /**
    * 根据ID删除
    * @param id
    * @return
    */
    boolean deleteById(Long id);

    /**
    * 根据ID更新
    * @param alarmRuleDO
    * @return
    */
    boolean updateById(AlarmRuleDO alarmRuleDO);

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    AlarmRuleDO queryById(Long id);

    /**
    * 分页查询
    * @param query
    * @return
    */
    PageResponse<AlarmRuleDO> page(AlarmRuleQuery query);

    /**
    * 列表查询
    * @param query
    * @return
    */
    List<AlarmRuleDO> list(AlarmRuleQuery query);
}
