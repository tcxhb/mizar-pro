package com.tcxhb.mizar.core.service.alarm.impl;

import java.util.ArrayList;
import java.util.List;

import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.utils.LocalCache;
import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.constants.enums.OnlineStatus;
import com.tcxhb.mizar.core.service.alarm.AlarmRuleService;
import com.tcxhb.mizar.dao.dataobject.AlarmRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.AlarmRuleQuery;
import com.tcxhb.mizar.dao.repository.AlarmRuleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * author:auto.generator
 * time: 2023-12-29
 */
@Service
public class AlarmRuleServiceImpl implements AlarmRuleService {
    @Resource
    private AlarmRuleRepository repository;
    private LocalCache<List<AlarmRuleDO>> allRule = new LocalCache<>();

    @Override
    public Long create(AlarmRuleDO mzAlarmRule) {
        return repository.create(mzAlarmRule);
    }

    @Override
    public boolean deleteById(Long id) {
        ParamUtils.notNull(id, "id不能为空");
        return repository.deleteById(id);
    }

    @Override
    public boolean updateById(AlarmRuleDO mzAlarmRule) {
        return repository.updateById(mzAlarmRule);
    }


    @Override
    public PageResponse<AlarmRuleDO> page(AlarmRuleQuery query) {
        return repository.page(query);
    }

    @Override
    public List<AlarmRuleDO> list(AlarmRuleQuery query) {
        return repository.list(query);
    }

    @Override
    public List<AlarmRuleDO> onlineCacheRule() {
        List<AlarmRuleDO> list = allRule.get("al");
        if (list != null) {
            return list;
        }
        //查询结果
        AlarmRuleQuery query = new AlarmRuleQuery();
        query.setStatus(OnlineStatus.online.getType());
        list = repository.list(query);
        if (list == null) {
            list = new ArrayList<>();
        }
        //allRule.put("al", list);
        return list;
    }

    @Override
    public AlarmRuleDO queryRuleByCode(String resource) {
        List<AlarmRuleDO> list = onlineCacheRule();
        for (AlarmRuleDO ruleDO : list) {
            if (ruleDO.getResource().equals(resource)) {
                return ruleDO;
            }
        }
        return null;
    }
}
