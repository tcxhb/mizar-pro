package com.tcxhb.mizar.core.service;

import com.tcxhb.mizar.dao.dataobject.MiscUserDO;
import com.tcxhb.mizar.dao.dataobject.query.MiscUserQuery;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.PageResponse;

import java.util.List;

/**
 * author:auto.generator
 * time: 2023-09-20
 */
public interface MiscUserService {
    /**
     * 创建
     *
     * @param miscUserDO
     * @return
     */
    Long create(MiscUserDO miscUserDO);

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 更新
     *
     * @param miscUserDO
     * @return
     */
    boolean updateById(MiscUserDO miscUserDO);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    MiscUserDO queryById(Long id);

    /**
     * 根据用户名登录
     *
     * @param usrname
     * @return
     */
    MiscUserDO queryByUsername(String usrname);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageResponse<MiscUserDO> page(MiscUserQuery query);

    /**
     * 列表查询
     *
     * @param query
     * @return
     */
    List<MiscUserDO> list(MiscUserQuery query);

    /**
     * 密码校验
     *
     * @param userName
     * @param password
     * @return
     */
    MiscResult<MiscUserDO> checkPassword(String userName, String password);
}
