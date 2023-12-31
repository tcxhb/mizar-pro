package com.tcxhb.mizar.core.service.biz;

import com.tcxhb.mizar.dao.dataobject.AppDO;
import com.tcxhb.mizar.dao.dataobject.query.AppQuery;
import com.tcxhb.mizar.common.model.PageResponse;

import java.util.List;
/**
* author:auto.generator
* time: 2023-11-02
*/
public interface AppService {
    /**
    * 创建
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
    * 更新
    * @param appDO
    * @return
    */
    boolean updateById(AppDO appDO);
    /**
    * 查询
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
     * 查询
     * @param appname
     * @return
     */
    AppDO queryByAppName(String appname, Boolean useCache);

    /**
     *
     * @param appname
     * @return
     */
    boolean updateVersion(String appname,Long version);

    List<String> allApp();

    void cleanCache(String app);
}
