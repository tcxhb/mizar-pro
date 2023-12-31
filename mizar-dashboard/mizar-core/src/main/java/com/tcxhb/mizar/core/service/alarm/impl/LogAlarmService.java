package com.tcxhb.mizar.core.service.alarm.impl;

import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.core.entity.AlarmMessage;
import com.tcxhb.mizar.core.service.alarm.AlarmPushService;
import com.tcxhb.mizar.core.service.alarm.AlarmRuleService;
import com.tcxhb.mizar.dao.dataobject.AlarmRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.AlarmRuleQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/30
 */
@Component
@Slf4j
public class LogAlarmService implements AlarmPushService {
    @Override
    public void push(AlarmMessage alarm) {
        String message = alarm.message();
        log.error("alarm:" + message);
    }
}
