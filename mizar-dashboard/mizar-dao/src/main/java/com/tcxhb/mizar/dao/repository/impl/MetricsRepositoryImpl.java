package com.tcxhb.mizar.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.dao.dataobject.query.MetricsQuery;
import com.tcxhb.mizar.dao.mapper.MetricsMapper;
import com.tcxhb.mizar.dao.repository.MetricsRepository;

import com.tcxhb.mizar.dao.dataobject.MetricsDO;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author:auto.generator
 * time: 2023-12-20
 */
@Repository
public class MetricsRepositoryImpl extends ServiceImpl<MetricsMapper, MetricsDO> implements MetricsRepository {
    private int batchSize = 100;

    @Override
    public boolean batchInsert(List<MetricsDO> list) {
        List<MetricsDO> list100 = new ArrayList<>(105);
        int n = list.size();
        for (int i = 0; i < n; i++) {
            list100.add(list.get(i));
            if (list100.size() < batchSize && i != n - 1) {
                continue;
            }
            try {
                boolean result = saveBatch(list, list.size());
                //重新赋值
                list100 = new ArrayList<>(105);
            } catch (Exception e) {
                list100 = new ArrayList<>(105);
            }
        }
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(MetricsDO metricsDO) {
        return super.updateById(metricsDO);
    }

    @Override
    public MetricsDO queryById(Long id) {
        return super.getById(id);
    }

    @Override
    public PageResponse<MetricsDO> page(MetricsQuery query) {
        LambdaQueryWrapper<MetricsDO> wrapper = queryWrapper(query);
        Page<MetricsDO> page = new Page<>(query.getPage().getPageNum(), query.getPage().getPageSize());
        IPage response = page(page, wrapper);
        PageResponse result = new PageResponse();
        result.setTotal(response.getTotal());
        result.setList(response.getRecords());
        return result;
    }

    @Override

    public List<MetricsDO> list(MetricsQuery query) {
        LambdaQueryWrapper<MetricsDO> wrapper = queryWrapper(query);
        return super.list(wrapper);
    }

    private LambdaQueryWrapper<MetricsDO> queryWrapper(MetricsQuery query) {
        LambdaQueryWrapper<MetricsDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MetricsDO::getApp, query.getApp());
        wrapper.eq(MetricsDO::getResource, query.getResource());
        wrapper.ge(MetricsDO::getTimestamp, new Date(query.getStartTime()));
        wrapper.le(MetricsDO::getTimestamp, new Date(query.getEndTime()));
        wrapper.orderByAsc(MetricsDO::getTimestamp);
        return wrapper;
    }

    @Override
    public boolean deleteHistory(Integer day) {
        if (day < 7) {
            throw new RuntimeException("day小于7");
        }
        Date now = new Date();
        UpdateWrapper<MetricsDO> wrapper = new UpdateWrapper<>();
        wrapper.le("timestamp", new Date(now.getTime() - day * 60 * 24 * 60 * 1000));
        wrapper.last(" limit 200000");
        return super.remove(wrapper);
    }
}
