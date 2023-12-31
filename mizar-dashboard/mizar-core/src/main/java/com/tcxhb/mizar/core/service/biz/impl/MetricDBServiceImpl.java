package com.tcxhb.mizar.core.service.biz.impl;

import com.tcxhb.mizar.common.utils.TimeUtil;
import com.tcxhb.mizar.core.convert.MetricConvert;
import com.tcxhb.mizar.core.entity.MetricEntity;
import com.tcxhb.mizar.core.service.biz.MetricDBService;
import com.tcxhb.mizar.dao.dataobject.MetricsDO;
import com.tcxhb.mizar.dao.dataobject.query.MetricsQuery;
import com.tcxhb.mizar.dao.repository.MetricsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/20
 */
@Component
public class MetricDBServiceImpl implements MetricDBService {
    private static Map<String, Long> lastTimeMap = new ConcurrentHashMap<>();
    //采样间隔1分钟
    private Long tiredTime = 60000L;
    @Autowired
    private MetricsRepository metricsRepository;

    @Override
    public void saveAll(String app, Iterable<MetricEntity> metrics) {
        //每隔1分钟插入一次DB
        if (checkDBTired(app)) {
            return;
        }
        //校验
        List<MetricsDO> metricDOS = new ArrayList<>();
        for (MetricEntity m : metrics) {
            metricDOS.add(MetricConvert.toDO(m));
        }
        //数量为0
        if (metricDOS.size() == 0) {
            return;
        }
        metricsRepository.batchInsert(metricDOS);
    }

    @Override
    public List<MetricEntity> queryByAppAndResourceBetween(MetricsQuery query) {
        List<MetricEntity> results = new ArrayList<>();
        if (StringUtils.isBlank(query.getApp())) {
            return results;
        }
        List<MetricsDO> list = metricsRepository.list(query);
        return list.stream().map(e -> MetricConvert.toEntity(e)).collect(Collectors.toList());
    }

    /**
     * 数据库疲劳度插入DB
     *
     * @return
     */
    private boolean checkDBTired(String app) {
        //疲劳校验
        Long lastTime = lastTimeMap.get(app);
        Long current = TimeUtil.currentTimeMillis();
        if (lastTime != null && (current - lastTime) < tiredTime) {
            return true;
        }
        lastTimeMap.put(app, current);
        return false;
    }
}
