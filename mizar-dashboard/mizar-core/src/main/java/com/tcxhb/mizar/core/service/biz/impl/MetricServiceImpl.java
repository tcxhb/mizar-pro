package com.tcxhb.mizar.core.service.biz.impl;

import com.tcxhb.mizar.common.utils.TimeUtil;
import com.tcxhb.mizar.core.entity.MetricEntity;
import com.tcxhb.mizar.core.schedule.monitor.BlockQpsMonitor;
import com.tcxhb.mizar.core.service.biz.MetricDBService;
import com.tcxhb.mizar.core.service.biz.MetricService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: tcxhb from sentinel
 * @Date: 2023/12/9
 */
@Component
public class MetricServiceImpl implements MetricService {

    @Autowired
    private MetricDBService metricDBService;
    private static final long MAX_METRIC_LIVE_TIME_MS = 1000 * 60 * 5;
    /**
     * {@code app -> resource -> timestamp -> metric}
     */
    private Map<String, Map<String, LinkedHashMap<Long, MetricEntity>>> allMetrics = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Autowired
    private BlockQpsMonitor blockQpsMonitor;

    @Override
    public void save(MetricEntity entity) {
        if (entity == null) {
            return;
        }
        readWriteLock.writeLock().lock();
        try {
            allMetrics.computeIfAbsent(entity.getApp(), e -> new HashMap<>(16))
                    .computeIfAbsent(entity.getResource(), e -> new LinkedHashMap<Long, MetricEntity>() {
                        @Override
                        protected boolean removeEldestEntry(Map.Entry<Long, MetricEntity> eldest) {
                            // Metric older than {@link #MAX_METRIC_LIVE_TIME_MS} will be removed.
                            return eldest.getKey() < TimeUtil.currentTimeMillis() - MAX_METRIC_LIVE_TIME_MS;
                        }
                    }).put(entity.getTimestamp().getTime(), entity);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void saveAll(String app, Iterable<MetricEntity> metrics) {
        if (metrics == null) {
            return;
        }
        List<MetricEntity> metricsIns = new ArrayList<>();
        metrics.forEach(e -> metricsIns.add(e));
        //插入内存
        readWriteLock.writeLock().lock();
        try {
            metricsIns.forEach(this::save);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        //插入DB
        metricDBService.saveAll(app, metricsIns);
        //添加监控
        blockQpsMonitor.work(app, metricsIns);
    }

    @Override
    public List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime) {
        List<MetricEntity> results = new ArrayList<>();
        Map<String, LinkedHashMap<Long, MetricEntity>> resourceMap = allMetrics.get(app);
        if (resourceMap == null) {
            return results;
        }
        LinkedHashMap<Long, MetricEntity> metricsMap = resourceMap.get(resource);
        if (metricsMap == null) {
            return results;
        }
        readWriteLock.readLock().lock();
        try {
            for (Map.Entry<Long, MetricEntity> entry : metricsMap.entrySet()) {
                if (entry.getKey() >= startTime && entry.getKey() <= endTime) {
                    results.add(entry.getValue());
                }
            }
            return results;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public List<String> listResourcesOfApp(String app) {
        List<String> results = new ArrayList<>();
        if (StringUtils.isBlank(app)) {
            return results;
        }
        // resource -> timestamp -> metric
        Map<String, LinkedHashMap<Long, MetricEntity>> resourceMap = allMetrics.get(app);
        if (resourceMap == null) {
            return results;
        }
        final long minTimeMs = System.currentTimeMillis() - 1000 * 60;
        Map<String, MetricEntity> resourceCount = new ConcurrentHashMap<>(32);

        readWriteLock.readLock().lock();
        try {
            for (Map.Entry<String, LinkedHashMap<Long, MetricEntity>> resourceMetrics : resourceMap.entrySet()) {
                for (Map.Entry<Long, MetricEntity> metrics : resourceMetrics.getValue().entrySet()) {
                    if (metrics.getKey() < minTimeMs) {
                        continue;
                    }
                    MetricEntity newEntity = metrics.getValue();
                    if (resourceCount.containsKey(resourceMetrics.getKey())) {
                        MetricEntity oldEntity = resourceCount.get(resourceMetrics.getKey());
                        oldEntity.addPassQps(newEntity.getPassQps());
                        oldEntity.addRtAndSuccessQps(newEntity.getRt(), newEntity.getSuccessQps());
                        oldEntity.addBlockQps(newEntity.getBlockQps());
                        oldEntity.addExceptionQps(newEntity.getExceptionQps());
                        oldEntity.addCount(1);
                    } else {
                        resourceCount.put(resourceMetrics.getKey(), MetricEntity.copyOf(newEntity));
                    }
                }
            }
            // Order by last minute b_qps DESC.
            return resourceCount.entrySet()
                    .stream()
                    .sorted((o1, o2) -> {
                        MetricEntity e1 = o1.getValue();
                        MetricEntity e2 = o2.getValue();
                        int t = e2.getBlockQps().compareTo(e1.getBlockQps());
                        if (t != 0) {
                            return t;
                        }
                        return e2.getPassQps().compareTo(e1.getPassQps());
                    })
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }


}
