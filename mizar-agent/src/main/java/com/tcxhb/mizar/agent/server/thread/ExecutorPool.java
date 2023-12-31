package com.tcxhb.mizar.agent.server.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Description: 任务完成之后通知服务器完成
 * @Auther: tcxhb
 * @Date: 2023/11/8
 */
@Component
@Slf4j
public class ExecutorPool {
    private ExecutorService threadPool = new ThreadPoolExecutor(
            2, 2,
            10, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    /**
     * callback接口
     */
    public void submit(Runnable runner) {
        try {
            threadPool.submit(runner);
        } catch (Exception e) {
            log.error("submit-pool-exp:{},{}", e);
        }
    }
}
