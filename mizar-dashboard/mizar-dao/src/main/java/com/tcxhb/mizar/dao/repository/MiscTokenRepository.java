package com.tcxhb.mizar.dao.repository;

import com.tcxhb.mizar.dao.dataobject.MiscTokenDO;

/**
* author:auto.generator
* time: 2023-09-21
*/
public interface MiscTokenRepository {

    /**
    * 创建对象
    * @param miscTokenDO
    * @return
    */
    Long create(MiscTokenDO miscTokenDO);

    /**
     *
     * @param token
     * @return
     */
    MiscTokenDO queryByToken(String token);

    /**
     *
     * @param token
     * @return
     */
    Boolean deleteByToken(String token);
}
