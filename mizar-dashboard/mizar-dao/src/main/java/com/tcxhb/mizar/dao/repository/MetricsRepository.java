package com.tcxhb.mizar.dao.repository;

import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.dao.dataobject.MetricsDO;
import com.tcxhb.mizar.dao.dataobject.query.MetricsQuery;

import java.util.List;

/**
 * author:auto.generator
 * time: 2023-12-20
 */
public interface MetricsRepository {
    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    boolean batchInsert(List<MetricsDO> list);

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 根据ID更新
     *
     * @param metricsDO
     * @return
     */
    boolean updateById(MetricsDO metricsDO);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    MetricsDO queryById(Long id);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageResponse<MetricsDO> page(MetricsQuery query);

    /**
     * 列表查询
     *
     * @param query
     * @return
     */
    List<MetricsDO> list(MetricsQuery query);

    /**
     * 清除数据
     *
     * @param day
     * @return
     */
    public boolean deleteHistory(Integer day);
}
