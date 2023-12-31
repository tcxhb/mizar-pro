package com.tcxhb.mizar.core.service.biz;

import com.tcxhb.mizar.dao.dataobject.MachineDO;
import com.tcxhb.mizar.dao.dataobject.query.MachineQuery;
import com.tcxhb.mizar.common.model.PageResponse;

import java.util.List;

/**
 * author:auto.generator
 * time: 2023-11-05
 */
public interface MachineService {
    /**
     * 处理心跳
     *
     * @param machineDO
     * @return
     */
    boolean beat(MachineDO machineDO);

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
     * @param machineDO
     * @return
     */
    boolean updateById(MachineDO machineDO);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    MachineDO queryById(Long id);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageResponse<MachineDO> page(MachineQuery query);

    /**
     * 列表查询
     *
     * @param query
     * @return
     */
    List<MachineDO> list(MachineQuery query);

    /**
     * 更新
     *
     * @param machineDO
     * @return
     */
    boolean updateByHost(MachineDO machineDO);

    /**
     * @param appName
     * @return
     */
    MachineDO queryByHost(String host, Integer prot, String appName);

    /**
     * 获取有效的机器列表
     *
     * @param app
     * @return
     */
    List<MachineDO> getOnlineByCache(String app);

    /**
     * 清理缓存
     *
     * @param app
     */
    void cleanCache(String app);
}
