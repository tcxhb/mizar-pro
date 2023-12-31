package com.tcxhb.mizar.core.service.biz.impl;

import com.tcxhb.mizar.common.constants.enums.ActionType;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.model.biz.FlowRuleConfig;
import com.tcxhb.mizar.common.model.biz.FlowRule;
import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.convert.FlowRuleConverter;
import com.tcxhb.mizar.core.service.biz.AppService;
import com.tcxhb.mizar.core.service.biz.FlowRuleService;
import com.tcxhb.mizar.dao.dataobject.AppDO;
import com.tcxhb.mizar.dao.dataobject.FlowRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.FlowRuleQuery;
import com.tcxhb.mizar.dao.repository.FlowRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author:auto.generator
 * time: 2023-12-14
 */
@Service
public class FlowRuleServiceImpl implements FlowRuleService {
    @Resource
    private FlowRuleRepository repository;
    @Autowired
    private AppService appService;
    @Autowired
    private FlowRuleConverter convert;

    @Override
    public Long create(FlowRuleDO ruleDO) {
        Boolean result = appService.updateVersion(ruleDO.getAppName(), System.currentTimeMillis());
        ParamUtils.isTrue(result, "版本更新失败");
        return repository.create(ruleDO);
    }

    @Override
    public boolean deleteById(Long id) {
        FlowRuleDO ruleDO = queryById(id);
        Boolean result = appService.updateVersion(ruleDO.getAppName(), System.currentTimeMillis());
        ParamUtils.isTrue(result, "版本更新失败");
        return repository.deleteById(id);
    }

    @Override
    public boolean updateById(FlowRuleDO ruleDO) {
        Boolean result = appService.updateVersion(ruleDO.getAppName(), System.currentTimeMillis());
        ParamUtils.isTrue(result, "版本更新失败");
        return repository.updateById(ruleDO);
    }

    @Override
    public FlowRuleDO queryById(Long id) {
        ParamUtils.notNull(id, "id不能为空");
        return repository.queryById(id);
    }

    @Override
    public PageResponse<FlowRuleDO> page(FlowRuleQuery query) {
        return repository.page(query);
    }

    @Override
    public FlowRuleConfig queryAgentRules(String appName) {
        AppDO appDO = appService.queryByAppName(appName, false);
        //查询规则
        FlowRuleQuery query = new FlowRuleQuery();
        query.setAppName(appName);
        List<FlowRuleDO> ruleDOS = repository.list(query);
        List<FlowRule> list = new ArrayList<>();
        //过滤限流规则
        for (FlowRuleDO ruleDO : ruleDOS) {
            if (ActionType.BLOCK.eq(ruleDO.getFlowAction())) {
                list.add(convert.do2Entity(ruleDO));
            }
        }
        FlowRuleConfig config = new FlowRuleConfig();
        config.setRuleList(list);
        if (appDO.getVersion() != null) {
            config.setVersion(appDO.getVersion());
        }
        return config;
    }
}
