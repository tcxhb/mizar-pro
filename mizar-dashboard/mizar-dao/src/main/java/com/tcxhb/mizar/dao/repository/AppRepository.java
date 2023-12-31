package com.tcxhb.mizar.dao.repository;

import com.tcxhb.mizar.dao.dataobject.AppDO;
import com.tcxhb.mizar.dao.dataobject.query.AppQuery;
import com.tcxhb.mizar.common.model.PageResponse;

import java.util.List;
/**
* author:auto.generator
* time: 2023-11-02
*/
public interface AppRepository {

    /**
    * 创建对象
    * @param appDO
    * @return
    */
    Long create(AppDO appDO);

    /**
    * 根据ID删除
    * @param id
    * @return
    */
    boolean deleteById(Long id);

    /**
    * 根据ID更新
    * @param appDO
    * @return
    */
    boolean updateById(AppDO appDO);

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    AppDO queryById(Long id);

    /**
    * 分页查询
    * @param query
    * @return
    */
    PageResponse<AppDO> page(AppQuery query);

    /**
    * 列表查询
    * @param query
    * @return
    */
    List<AppDO> list(AppQuery query);

    /**
     * 单个查询
     * @return
     */
    AppDO queryByAppName(String appName);

    /**
     * 根据名字来更新
     * @param appDO
     * @return
     */
    boolean updateByAppName(AppDO appDO);

}
