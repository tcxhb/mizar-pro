package com.tcxhb.mizar.core.schedule;

import com.tcxhb.mizar.common.Thread.NamedThreadFactory;
import com.tcxhb.mizar.core.schedule.worker.MetricFetcherWorker;
import com.tcxhb.mizar.core.service.biz.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/9
 */
@Slf4j
@Component
public class MetricFetcher {

    private static final long MAX_LAST_FETCH_INTERVAL_MS = 1000 * 15;
    private static final long FETCH_INTERVAL_SECOND = 6;

    private ScheduledExecutorService fetchScheduleService = Executors.newScheduledThreadPool(1,
            new NamedThreadFactory("metrics-fetch-task", true));
    @Autowired
    private AppService appService;
    private ExecutorService fetchService;
    @Autowired
    private MetricFetcherWorker metricFetcherWorker;

    private Map<String, AtomicLong> appLastFetchTime = new ConcurrentHashMap<>();

    @PostConstruct
    public void start() {
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        fetchService = new ThreadPoolExecutor(cores, cores,
                0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2048),
                new NamedThreadFactory("metrics-fetchService", true),
                new ThreadPoolExecutor.DiscardPolicy());

        fetchScheduleService.scheduleAtFixedRate(() -> {
            try {
                fetchAllApp();
            } catch (Exception e) {
                log.error("agent-check-schedule-exp", e);
            }
        }, 30, 5, TimeUnit.SECONDS);
    }

    private void fetchAllApp() {
        List<String> apps = appService.allApp();
        if (apps == null) {
            return;
        }
        for (final String app : apps) {
            fetchService.submit(() -> {
                try {
                    doFetchAppMetric(app);
                } catch (Exception e) {
                    log.error("fetchAppMetric error", e);
                }
            });
        }
    }

    private void doFetchAppMetric(final String app) {
        long now = System.currentTimeMillis();
        long lastFetchMs = now - MAX_LAST_FETCH_INTERVAL_MS;
        if (appLastFetchTime.containsKey(app)) {
            lastFetchMs = Math.max(lastFetchMs, appLastFetchTime.get(app).get() + 1000);
        }
        // trim milliseconds
        lastFetchMs = lastFetchMs / 1000 * 1000;
        long endTime = lastFetchMs + FETCH_INTERVAL_SECOND * 1000;
        if (endTime > now - 1000 * 2) {
            // too near
            return;
        }
        // update last_fetch in advance.
        appLastFetchTime.computeIfAbsent(app, a -> new AtomicLong()).set(endTime);
        final long finalLastFetchMs = lastFetchMs;
        final long finalEndTime = endTime;
        metricFetcherWorker.doFetchAppMetric(app, finalLastFetchMs, finalEndTime);
    }
}
