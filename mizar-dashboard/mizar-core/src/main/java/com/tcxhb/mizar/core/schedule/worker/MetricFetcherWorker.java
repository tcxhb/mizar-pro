package com.tcxhb.mizar.core.schedule.worker;

import com.tcxhb.mizar.common.Thread.NamedThreadFactory;
import com.tcxhb.mizar.common.constants.ApiConstants;
import com.tcxhb.mizar.common.constants.ResourceConstants;
import com.tcxhb.mizar.common.model.biz.MetricNode;
import com.tcxhb.mizar.core.entity.MetricEntity;
import com.tcxhb.mizar.core.service.biz.MachineService;
import com.tcxhb.mizar.core.service.biz.MetricService;
import com.tcxhb.mizar.dao.dataobject.MachineDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/9
 */
@Slf4j
@Component
public class MetricFetcherWorker {
    @Autowired
    private CloseableHttpAsyncClient asyncClient;
    @Autowired
    private MetricService metricService;
    private ExecutorService fetchWorker;
    private static final int HTTP_OK = 200;
    private static final int maxWaitSeconds = 60;
    @Autowired
    private MachineService machineService;
    @Autowired
    private MachineStatusWorker machineStatusWorker;

    @PostConstruct
    private void init() {
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        fetchWorker = new ThreadPoolExecutor(cores, cores,
                0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2048),
                new NamedThreadFactory("mizar-metrics-fetchWorker", true),
                new ThreadPoolExecutor.DiscardPolicy());

    }

    public void doFetchAppMetric(String app, long startTime, long endTime) {
        try {
            // do real fetch async
            fetchWorker.submit(() -> {
                try {
                    fetchOnce(app, startTime, endTime);
                } catch (Exception e) {
                    log.info("fetchOnce(" + app + ") error", e);
                }
            });
        } catch (Exception e) {
            log.info("submit fetchOnce(" + app + ") fail, intervalMs [" + startTime + ", " + endTime + "]", e);
        }
    }

    private void fetchOnce(String app, long startTime, long endTime) {
        List<MachineDO> machineList = machineService.getOnlineByCache(app);
        //机器列表
        final Map<String, MetricEntity> metricMap = new ConcurrentHashMap<>(16);
        final Map<String, String> systemMetric = new ConcurrentHashMap<>(16);
        final CountDownLatch latch = new CountDownLatch(machineList.size());
        for (final MachineDO machine : machineList) {
            //请求接口
            final String url = machine.httpAddress() + ApiConstants.cmd_query_qps
                    + "?startTime=" + startTime + "&endTime=" + endTime;

            final HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            asyncClient.execute(httpGet, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(final HttpResponse response) {
                    try {
                        log.info("getMetricFromMachine:" + url);
                        handleResponse(response, machine, metricMap, systemMetric);
                    } catch (Exception e) {
                        log.error("fetch metric " + url + " error:", e);
                    } finally {
                        latch.countDown();
                    }
                }

                @Override
                public void failed(final Exception ex) {
                    latch.countDown();
                    httpGet.abort();
                    if (ex instanceof SocketTimeoutException) {
                        machineStatusWorker.work(machine);
                        log.error("Failed to fetch metric from <{}>: socket timeout", url);
                    } else if (ex instanceof ConnectException) {
                        machineStatusWorker.work(machine);
                        log.error("Failed to fetch metric from <{}> (ConnectionException: {})", url, ex.getMessage());
                    } else {
                        log.error("fetch metric " + url + " error", ex);
                    }
                }

                @Override
                public void cancelled() {
                    latch.countDown();
                    httpGet.abort();
                }
            });
        }
        try {
            latch.await(maxWaitSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("fetch metric, wait http client error:", e);
        }
        //保存数据
        writeMetric(app, metricMap, systemMetric);
    }

    private void writeMetric(String app, Map<String, MetricEntity> metricMap, Map<String, String> stringMap) {
        Date date = new Date();
        for (MetricEntity entity : metricMap.values()) {
            entity.setGmtCreate(date);
            entity.setGmtModified(date);
        }
        metricService.saveAll(app, metricMap.values());
    }

    private void handleResponse(final HttpResponse response, MachineDO machine, Map<String, MetricEntity> map, Map<String, String> sysMetric) throws Exception {
        int code = response.getStatusLine().getStatusCode();
        if (code != HTTP_OK) {
            return;
        }
        String body = EntityUtils.toString(response.getEntity());
        if (StringUtils.isBlank(body)) {
            log.error("fetch metric error,{}", machine.getIp());
            return;
        }
        //error
        if (body.startsWith("error")) {
            log.error("fetch metric error,{},{}", machine.getIp(), body);
            return;
        }
        handleBody(body, machine, map, sysMetric);
    }

    private void handleBody(String result, MachineDO machine, Map<String, MetricEntity> map, Map<String, String> sysMetric) {
        String[] lines = result.split("\n");
        for (String line : lines) {
            if (StringUtils.isBlank(line)) {
                continue;
            }
            //系统资源逻辑处理
            if (isSystemResource(line)) {
                sysMetric.put(machine.getIp(), line);
                continue;
            }
            try {
                MetricNode node = MetricNode.fromThinString(line);
                convert(map, node, machine);
            } catch (Exception e) {
                log.error("metric format error:" + line);
            }
        }
    }

    private boolean isSystemResource(String line) {
        if (line.startsWith(ResourceConstants.cpu)) {
            return true;
        }
        return false;
    }

    private String buildMetricKey(String app, String resource, long timestamp) {
        return app + "_" + resource + "_" + (timestamp / 1000);
    }

    private MetricEntity convert(Map<String, MetricEntity> map, MetricNode node, MachineDO machine) {
        String key = buildMetricKey(machine.getAppName(), node.getResource(), node.getTimestamp());
        MetricEntity metricEntity = map.computeIfAbsent(key, s -> {
            MetricEntity initMetricEntity = new MetricEntity();
            initMetricEntity.setApp(machine.getAppName());
            initMetricEntity.setTimestamp(new Date(node.getTimestamp()));
            initMetricEntity.setPassQps(0L);
            initMetricEntity.setBlockQps(0L);
            initMetricEntity.setRtAndSuccessQps(0, 0L);
            initMetricEntity.setExceptionQps(0L);
            initMetricEntity.setCount(0);
            initMetricEntity.setResource(node.getResource());
            return initMetricEntity;
        });
        metricEntity.addPassQps(node.getPassQps());
        metricEntity.addBlockQps(node.getBlockQps());
        metricEntity.addRtAndSuccessQps(node.getRt(), node.getSuccessQps());
        metricEntity.addExceptionQps(node.getExceptionQps());
        metricEntity.addCount(1);
        return metricEntity;
    }
}
