package com.tcxhb.mizar.core.service.impl;

import com.tcxhb.mizar.dao.dataobject.MiscTokenDO;
import com.tcxhb.mizar.dao.repository.MiscTokenRepository;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.convert.MiscTokenConverter;
import com.tcxhb.mizar.core.entity.TokenDTO;
import com.tcxhb.mizar.core.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * author:auto.generator
 * time: 2023-09-21
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    private MiscTokenRepository repository;
    @Autowired
    private MiscTokenConverter convert;

    private static Integer expireTime = 3600 * 12 * 1000;

    @Override
    public Long save(TokenDTO miscToken) {
        MiscTokenDO tokenDO = convert.dto2DO(miscToken);
        Long current = System.currentTimeMillis();
        tokenDO.setExpireTime(current + expireTime);
        return repository.create(tokenDO);
    }


    @Override
    public MiscResult<TokenDTO> queryByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return MiscResult.err(CommonErrorCode.NEED_LOGIN.getCode(), "x-token为空请重新登录");
        }
        MiscTokenDO tokenDO = repository.queryByToken(token);
        if (tokenDO == null) {
            return MiscResult.err(CommonErrorCode.NEED_LOGIN.getCode(), "无效token请重新登录");
        }
        //判断是否失效
        Long current = System.currentTimeMillis();
        if (tokenDO.getExpireTime() < current) {
            return MiscResult.err(CommonErrorCode.NEED_LOGIN.getCode(), "token失效请重新登录");
        }
        TokenDTO dto = convert.do2DTO(tokenDO);
        return MiscResult.suc(dto);
    }

    @Override
    public Boolean deleteByToken(String token) {
        ParamUtils.notNull(token, "id不能为空");
        return repository.deleteByToken(token);
    }
}
