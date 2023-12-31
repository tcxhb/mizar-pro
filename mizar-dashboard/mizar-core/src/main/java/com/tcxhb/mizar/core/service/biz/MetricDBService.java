package com.tcxhb.mizar.core.service.biz;

import com.tcxhb.mizar.core.entity.MetricEntity;
import com.tcxhb.mizar.dao.dataobject.query.MetricsQuery;

import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/20
 */
public interface MetricDBService {
    /**
     * 插入数据库
     *
     * @param app
     * @param metrics
     */
    void saveAll(String app, Iterable<MetricEntity> metrics);

    /**
     * 查询
     *
     * @param query
     * @return
     */
    public List<MetricEntity> queryByAppAndResourceBetween(MetricsQuery query);
}
