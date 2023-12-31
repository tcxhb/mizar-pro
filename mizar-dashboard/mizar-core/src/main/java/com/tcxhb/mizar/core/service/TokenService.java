package com.tcxhb.mizar.core.service;

import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.core.entity.TokenDTO;

/**
 * author:auto.generator
 * time: 2023-09-21
 */
public interface TokenService {
    /**
     * 创建
     *
     * @param tokenDTO
     * @return
     */
    Long save(TokenDTO tokenDTO);

    /**
     * @param token
     * @return
     */
    MiscResult<TokenDTO> queryByToken(String token);

    /**
     * 失效过期
     *
     * @param token
     * @return
     */
    Boolean deleteByToken(String token);
}
