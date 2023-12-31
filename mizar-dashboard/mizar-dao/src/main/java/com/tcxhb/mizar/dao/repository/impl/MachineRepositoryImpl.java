package com.tcxhb.mizar.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcxhb.mizar.dao.dataobject.MachineDO;
import com.tcxhb.mizar.dao.dataobject.query.MachineQuery;
import com.tcxhb.mizar.dao.mapper.MachineMapper;
import com.tcxhb.mizar.dao.repository.MachineRepository;
import com.tcxhb.mizar.common.model.PageResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * author:auto.generator
 * time: 2023-11-05
 */
@Repository
public class MachineRepositoryImpl extends ServiceImpl<MachineMapper, MachineDO> implements MachineRepository {

    @Override
    public Long create(MachineDO machineDO) {
        super.save(machineDO);
        return machineDO.getId();
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(MachineDO machineDO) {
        return super.updateById(machineDO);
    }

    @Override
    public MachineDO queryById(Long id) {
        return super.getById(id);
    }

    @Override
    public PageResponse<MachineDO> page(MachineQuery query) {
        LambdaQueryWrapper<MachineDO> wrapper = queryWrapper(query);
        Page<MachineDO> page = new Page<>(query.getPage().getPageNum(), query.getPage().getPageSize());
        IPage response = page(page, wrapper);
        PageResponse result = new PageResponse();
        result.setTotal(response.getTotal());
        result.setList(response.getRecords());
        return result;
    }

    @Override
    public List<MachineDO> list(MachineQuery query) {
        LambdaQueryWrapper<MachineDO> wrapper = queryWrapper(query);
        if (query.getPage() != null) {
            wrapper.last(" limit " + query.getPage().getPageSize());
        }
        return super.list(wrapper);
    }

    @Override
    public boolean updateByHost(MachineDO machineDO) {
        UpdateWrapper<MachineDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ip", machineDO.getIp());
        updateWrapper.eq("port", machineDO.getPort());
        updateWrapper.eq("app_name", machineDO.getAppName());
        updateWrapper.set("status", machineDO.getStatus());
        updateWrapper.set("update_time", new Date());
        return super.update(updateWrapper);
    }

    private LambdaQueryWrapper<MachineDO> queryWrapper(MachineQuery query) {
        LambdaQueryWrapper<MachineDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getId() != null, MachineDO::getId, query.getId());
        if (StringUtils.isNotBlank(query.getIp())) {
            wrapper.eq(MachineDO::getIp, query.getIp());
        }
        //id 翻页
        if (query.getLastId() != null) {
            wrapper.gt(MachineDO::getId, query.getLastId());
        }
        //心跳时间大于
        if (query.getBeatTimeStart() != null) {
            wrapper.ge(MachineDO::getBeatTime, query.getBeatTimeStart());
        }
        //心跳时间小于
        if (query.getBeatTimeEnd() != null) {
            wrapper.le(MachineDO::getBeatTime, query.getBeatTimeEnd());
        }
        wrapper.eq(query.getPort() != null, MachineDO::getPort, query.getPort());
        wrapper.eq(query.getStatus() != null, MachineDO::getStatus, query.getStatus());
        if (StringUtils.isNotBlank(query.getAppName())) {
            wrapper.eq(MachineDO::getAppName, query.getAppName());
        }
        //根据更新时间
        if ("id".equals(query.getAscKey())) {
            wrapper.orderByAsc(MachineDO::getId);
        } else {
            wrapper.orderByDesc(MachineDO::getUpdateTime);
        }
        return wrapper;
    }
}
