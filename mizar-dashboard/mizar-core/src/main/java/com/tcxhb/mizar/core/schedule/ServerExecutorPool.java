package com.tcxhb.mizar.core.schedule;

import com.tcxhb.mizar.common.Thread.NamedThreadFactory;
import com.tcxhb.mizar.dao.repository.MetricsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/17
 */
@Component
@Slf4j
public class ServerExecutorPool {
    @Autowired
    private MetricsRepository metricsRepository;

    private static ExecutorService worker = new ThreadPoolExecutor(5, 5,
            10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1024),
            new NamedThreadFactory("metrics-pool", true),
            new ThreadPoolExecutor.DiscardPolicy());

    public void submit(Runnable runnable) {
        try {
            worker.submit(runnable);
        } catch (Exception e) {
            log.error("submit", e);
        }
    }


    @Scheduled(cron = "0 0 3 * * ?") //每天0点执行
    public void deleteHistory() {
        try {
            log.info("deleteHistory start");
            metricsRepository.deleteHistory(7);
            log.info("deleteHistory finish");
        } catch (Exception e) {
            log.info("deleteHistory error", e);
        }
    }
}
