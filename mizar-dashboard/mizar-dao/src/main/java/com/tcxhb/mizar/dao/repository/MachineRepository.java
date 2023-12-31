package com.tcxhb.mizar.dao.repository;

import com.tcxhb.mizar.dao.dataobject.MachineDO;
import com.tcxhb.mizar.dao.dataobject.query.MachineQuery;
import com.tcxhb.mizar.common.model.PageResponse;

import java.util.List;
/**
* author:auto.generator
* time: 2023-11-05
*/
public interface MachineRepository {

    /**
    * 创建对象
    * @param machineDO
    * @return
    */
    Long create(MachineDO machineDO);

    /**
    * 根据ID删除
    * @param id
    * @return
    */
    boolean deleteById(Long id);

    /**
    * 根据ID更新
    * @param machineDO
    * @return
    */
    boolean updateById(MachineDO machineDO);

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    MachineDO queryById(Long id);

    /**
    * 分页查询
    * @param query
    * @return
    */
    PageResponse<MachineDO> page(MachineQuery query);

    /**
    * 列表查询
    * @param query
    * @return
    */
    List<MachineDO> list(MachineQuery query);

    /**
     * 更新
     * @param machineDO
     * @return
     */
    boolean updateByHost(MachineDO machineDO);
}
