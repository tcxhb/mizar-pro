package com.tcxhb.mizar.core.service.biz;

import com.tcxhb.mizar.core.entity.MetricEntity;

import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/9
 */
public interface MetricService {

    void save(MetricEntity metric);

    void saveAll(String app, Iterable<MetricEntity> metrics);

    List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime);

    List<String> listResourcesOfApp(String app);
}
