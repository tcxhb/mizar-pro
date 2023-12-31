package com.tcxhb.mizar.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcxhb.mizar.dao.dataobject.AppDO;
import com.tcxhb.mizar.dao.dataobject.query.AppQuery;
import com.tcxhb.mizar.dao.mapper.AppMapper;
import com.tcxhb.mizar.dao.repository.AppRepository;
import com.tcxhb.mizar.common.model.PageResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * author:auto.generator
 * time: 2023-11-02
 */
@Repository
public class AppRepositoryImpl extends ServiceImpl<AppMapper, AppDO> implements AppRepository {

    @Override
    public Long create(AppDO appDO) {
        super.save(appDO);
        return appDO.getId();
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(AppDO appDO) {
        return super.updateById(appDO);
    }

    @Override
    public AppDO queryById(Long id) {
        return super.getById(id);
    }

    @Override
    public PageResponse<AppDO> page(AppQuery query) {
        LambdaQueryWrapper<AppDO> wrapper = queryWrapper(query);
        Page<AppDO> page = new Page<>(query.getPage().getPageNum(), query.getPage().getPageSize());
        IPage response = page(page, wrapper);
        PageResponse result = new PageResponse();
        result.setTotal(response.getTotal());
        result.setList(response.getRecords());
        return result;
    }

    @Override
    public List<AppDO> list(AppQuery query) {
        LambdaQueryWrapper<AppDO> wrapper = queryWrapper(query);
        return super.list(wrapper);
    }

    @Override
    public AppDO queryByAppName(String appName) {
        LambdaQueryWrapper<AppDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AppDO::getAppName, appName);
        return super.getOne(wrapper);
    }

    @Override
    public boolean updateByAppName(AppDO appDO) {
        UpdateWrapper<AppDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("app_name", appDO.getAppName());
        if (appDO.getVersion() != null) {
            updateWrapper.set("version", appDO.getVersion());
        }
        updateWrapper.set("update_time", new Date());
        return super.update(updateWrapper);
    }

    private LambdaQueryWrapper<AppDO> queryWrapper(AppQuery query) {
        LambdaQueryWrapper<AppDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getId() != null, AppDO::getId, query.getId());
        if (StringUtils.isNotBlank(query.getAppName())) {
            wrapper.eq(AppDO::getAppName, query.getAppName());
        }
        return wrapper;
    }
}
