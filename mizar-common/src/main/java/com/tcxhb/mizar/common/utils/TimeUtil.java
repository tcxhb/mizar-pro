package com.tcxhb.mizar.common.utils;

import java.util.concurrent.TimeUnit;

/**
 * 时间计算工具
 *
 * @author wujiuye
 */
public final class TimeUtil {

    private static volatile long currentTimeMillis;

    static {
        currentTimeMillis = System.currentTimeMillis();
        Thread daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    currentTimeMillis = System.currentTimeMillis();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (Throwable e) {

                    }
                }
            }
        });
        daemon.setDaemon(true);
        daemon.setName("timeUtil-time-tick-thread");
        daemon.start();
    }
    public static long currentTimeMillis() {
        return currentTimeMillis;
    }
}
