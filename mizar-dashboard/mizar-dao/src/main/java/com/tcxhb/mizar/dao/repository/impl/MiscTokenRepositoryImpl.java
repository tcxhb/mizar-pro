package com.tcxhb.mizar.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcxhb.mizar.dao.dataobject.MiscTokenDO;
import com.tcxhb.mizar.dao.mapper.MiscTokenMapper;
import com.tcxhb.mizar.dao.repository.MiscTokenRepository;
import org.springframework.stereotype.Repository;
/**
 * author:auto.generator
 * time: 2023-09-21
 */
@Repository
public class MiscTokenRepositoryImpl extends ServiceImpl<MiscTokenMapper, MiscTokenDO> implements MiscTokenRepository {

    @Override
    public Long create(MiscTokenDO miscTokenDO) {
        super.save(miscTokenDO);
        return miscTokenDO.getId();
    }

    @Override
    public MiscTokenDO queryByToken(String token) {
        LambdaQueryWrapper<MiscTokenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MiscTokenDO::getToken, token);
        return getOne(wrapper);
    }

    @Override
    public Boolean deleteByToken(String token) {
        LambdaQueryWrapper<MiscTokenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MiscTokenDO::getToken, token);
        return super.remove(wrapper);
    }
}
