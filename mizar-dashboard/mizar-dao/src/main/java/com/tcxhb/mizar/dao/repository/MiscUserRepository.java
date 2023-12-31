package com.tcxhb.mizar.dao.repository;

import com.tcxhb.mizar.dao.dataobject.MiscUserDO;
import com.tcxhb.mizar.dao.dataobject.query.MiscUserQuery;
import com.tcxhb.mizar.common.model.PageResponse;

import java.util.List;
/**
* author:auto.generator
* time: 2023-09-20
*/
public interface MiscUserRepository {

    /**
    * 创建对象
    * @param miscUserDO
    * @return
    */
    Long create(MiscUserDO miscUserDO);

    /**
    * 根据ID删除
    * @param id
    * @return
    */
    boolean deleteById(Long id);

    /**
    * 根据ID更新
    * @param miscUserDO
    * @return
    */
    boolean updateById(MiscUserDO miscUserDO);

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    MiscUserDO queryById(Long id);

    /**
     *
     * @param username
     * @return
     */
    MiscUserDO queryByUsername(String username);
    /**
    * 分页查询
    * @param query
    * @return
    */
    PageResponse<MiscUserDO> page(MiscUserQuery query);

    /**
    * 列表查询
    * @param query
    * @return
    */
    List<MiscUserDO> list(MiscUserQuery query);
}
