package com.tcxhb.mizar.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.dao.dataobject.AlarmRuleDO;
import com.tcxhb.mizar.dao.dataobject.FlowRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.AlarmRuleQuery;
import com.tcxhb.mizar.dao.mapper.AlarmRuleMapper;
import com.tcxhb.mizar.dao.repository.AlarmRuleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:auto.generator
 * time: 2023-12-29
 */
@Repository
public class AlarmRuleRepositoryImpl extends ServiceImpl<AlarmRuleMapper, AlarmRuleDO> implements AlarmRuleRepository {

    @Override
    public Long create(AlarmRuleDO alarmRuleDO) {
        super.save(alarmRuleDO);
        return alarmRuleDO.getId();
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(AlarmRuleDO alarmRuleDO) {
        return super.updateById(alarmRuleDO);
    }

    @Override
    public AlarmRuleDO queryById(Long id) {
        return super.getById(id);
    }

    @Override
    public PageResponse<AlarmRuleDO> page(AlarmRuleQuery query) {
        LambdaQueryWrapper<AlarmRuleDO> wrapper = queryWrapper(query);
        Page<AlarmRuleDO> page = new Page<>(query.getPage().getPageNum(), query.getPage().getPageSize());
        IPage response = page(page, wrapper);
        PageResponse result = new PageResponse();
        result.setTotal(response.getTotal());
        result.setList(response.getRecords());
        return result;
    }

    @Override
    public List<AlarmRuleDO> list(AlarmRuleQuery query) {
        LambdaQueryWrapper<AlarmRuleDO> wrapper = queryWrapper(query);
        return super.list(wrapper);
    }

    private LambdaQueryWrapper<AlarmRuleDO> queryWrapper(AlarmRuleQuery query) {
        LambdaQueryWrapper<AlarmRuleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getStatus() != null, AlarmRuleDO::getStatus, query.getStatus());
        //资源code
        if (StringUtils.isNotBlank(query.getResource())) {
            wrapper.like(AlarmRuleDO::getResource, query.getResource());
        }
        return wrapper;
    }
}
