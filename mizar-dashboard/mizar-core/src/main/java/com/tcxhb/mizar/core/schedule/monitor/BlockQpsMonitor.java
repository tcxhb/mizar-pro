package com.tcxhb.mizar.core.schedule.monitor;

import com.tcxhb.mizar.core.entity.AlarmMessage;
import com.tcxhb.mizar.core.entity.AppearCounter;
import com.tcxhb.mizar.core.entity.MetricEntity;
import com.tcxhb.mizar.core.schedule.ServerExecutorPool;
import com.tcxhb.mizar.core.service.alarm.AlarmPushService;
import com.tcxhb.mizar.core.service.alarm.AlarmRuleService;
import com.tcxhb.mizar.core.utils.ResourceUtils;
import com.tcxhb.mizar.dao.dataobject.AlarmRuleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/29
 */
@Component
public class BlockQpsMonitor {
    @Autowired
    private AlarmRuleService alarmRuleService;
    @Autowired
    private ServerExecutorPool serverExecutorPool;
    private static String RESOURCE_NAME = "blockQps";
    private Map<String, AppearCounter> counterMap = new ConcurrentHashMap<String, AppearCounter>();

    @Autowired
    private AlarmPushService alarmPushService;

    public void work(String app, List<MetricEntity> metrics) {
        if (CollectionUtils.isEmpty(metrics)) {
            return;
        }
        serverExecutorPool.submit(() -> this.monitor(app, metrics));
    }

    /**
     * @param app
     * @param metrics
     */
    private void monitor(String app, List<MetricEntity> metrics) {
        AlarmRuleDO rule = alarmRuleService.queryRuleByCode(RESOURCE_NAME);
        if (rule == null || !rule.getStatus()) {
            return;
        }
        //触发告警
        for (MetricEntity metric : metrics) {
            if (checkAlarm(app, metric, rule)) {
                //触发告警逻辑
                AlarmMessage push = new AlarmMessage();
                push.setApp(app);
                push.setResource(metric.getResource());
                push.setMsg(rule.getAlarmMsg());
                push.setValue(metric.getPassQps() + metric.getBlockQps());
                alarmPushService.push(push);
                return;
            }
        }
    }

    /**
     * 校验是否触发告警
     *
     * @param app
     * @param metric
     * @param rule
     * @return
     */
    public boolean checkAlarm(String app, MetricEntity metric, AlarmRuleDO rule) {
        //校验是否大于阀值
        if (metric.getBlockQps() <= rule.getThreshold()) {
            return false;
        }
        //以下是次数校验机器缓存的key
        String sourceKey = ResourceUtils.getResourceKey(app, metric.getResource());
        AppearCounter counter = counterMap.computeIfAbsent(sourceKey, s -> {
            return new AppearCounter(rule.getTimeInterval(), rule.getAppearLimit());
        });
        //校验是否触发告警
        if (counter.addAndCheck(metric.getTimestamp().getTime())) {
            return true;
        }
        return false;
    }
}
