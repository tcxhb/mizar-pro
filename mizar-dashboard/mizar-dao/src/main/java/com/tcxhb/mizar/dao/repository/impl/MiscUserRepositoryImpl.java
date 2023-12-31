package com.tcxhb.mizar.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcxhb.mizar.dao.dataobject.MiscUserDO;
import com.tcxhb.mizar.dao.dataobject.query.MiscUserQuery;
import com.tcxhb.mizar.dao.mapper.MiscUserMapper;
import com.tcxhb.mizar.dao.repository.MiscUserRepository;
import com.tcxhb.mizar.common.model.PageResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * author:auto.generator
 * time: 2023-09-20
 */
@Repository
public class MiscUserRepositoryImpl extends ServiceImpl<MiscUserMapper, MiscUserDO> implements MiscUserRepository {

    @Override
    public Long create(MiscUserDO miscUserDO) {
        super.save(miscUserDO);
        return miscUserDO.getId();
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(MiscUserDO miscUserDO) {
        return super.updateById(miscUserDO);
    }

    @Override
    public MiscUserDO queryById(Long id) {
        return super.getById(id);
    }

    @Override
    public MiscUserDO queryByUsername(String username) {
        LambdaQueryWrapper<MiscUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq( MiscUserDO::getUsername, username);
        return super.getOne(wrapper);
    }

    @Override
    public PageResponse<MiscUserDO> page(MiscUserQuery query) {
        LambdaQueryWrapper<MiscUserDO> wrapper = queryWrapper(query);
        Page<MiscUserDO> page = new Page<>(query.getPage().getPageNum(), query.getPage().getPageSize());
        IPage response = page(page, wrapper);
        PageResponse result = new PageResponse();
        result.setTotal(response.getTotal());
        result.setList(response.getRecords());
        return result;
    }

    @Override
    public List<MiscUserDO> list(MiscUserQuery query) {
        LambdaQueryWrapper<MiscUserDO> wrapper = queryWrapper(query);
        return super.list(wrapper);
    }

    private LambdaQueryWrapper<MiscUserDO> queryWrapper(MiscUserQuery query) {
        LambdaQueryWrapper<MiscUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getId() != null, MiscUserDO::getId, query.getId());
        //匹配
        if(StringUtils.isNotBlank(query.getUsername())){
            wrapper.like(MiscUserDO::getUsername,query.getUsername());
        }
        if(StringUtils.isNotBlank(query.getName())){
            wrapper.like(MiscUserDO::getName,query.getName());
        }
        return wrapper;
    }
}
