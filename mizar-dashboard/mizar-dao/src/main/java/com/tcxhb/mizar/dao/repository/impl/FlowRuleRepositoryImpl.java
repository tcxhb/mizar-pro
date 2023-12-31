package com.tcxhb.mizar.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.dao.dataobject.FlowRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.FlowRuleQuery;
import com.tcxhb.mizar.dao.mapper.FlowRuleMapper;
import com.tcxhb.mizar.dao.repository.FlowRuleRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:auto.generator
 * time: 2023-12-14
 */
@Repository
public class FlowRuleRepositoryImpl extends ServiceImpl<FlowRuleMapper, FlowRuleDO> implements FlowRuleRepository {

    @Override
    public Long create(FlowRuleDO flowRuleDO) {
        super.save(flowRuleDO);
        return flowRuleDO.getId();
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(FlowRuleDO flowRuleDO) {
        return super.updateById(flowRuleDO);
    }

    @Override
    public FlowRuleDO queryById(Long id) {
        return super.getById(id);
    }

    @Override
    public PageResponse<FlowRuleDO> page(FlowRuleQuery query) {
        LambdaQueryWrapper<FlowRuleDO> wrapper = queryWrapper(query);
        Page<FlowRuleDO> page = new Page<>(query.getPage().getPageNum(), query.getPage().getPageSize());
        IPage response = page(page, wrapper);
        PageResponse result = new PageResponse();
        result.setTotal(response.getTotal());
        result.setList(response.getRecords());
        return result;
    }

    @Override
    public List<FlowRuleDO> list(FlowRuleQuery query) {
        LambdaQueryWrapper<FlowRuleDO> wrapper = queryWrapper(query);
        return super.list(wrapper);
    }

    private LambdaQueryWrapper<FlowRuleDO> queryWrapper(FlowRuleQuery query) {
        LambdaQueryWrapper<FlowRuleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getId() != null, FlowRuleDO::getId, query.getId());
        if (StringUtils.isNotBlank(query.getAppName())) {
            wrapper.eq(FlowRuleDO::getAppName, query.getAppName());
        }
        //
        if (StringUtils.isNotBlank(query.getFlowAction())) {
            wrapper.eq(FlowRuleDO::getFlowAction, query.getFlowAction());
        }
        //资源code
        if (StringUtils.isNotBlank(query.getResourceCode())) {
            wrapper.like(FlowRuleDO::getResourceCode, query.getResourceCode());
        }
        return wrapper;
    }
}
