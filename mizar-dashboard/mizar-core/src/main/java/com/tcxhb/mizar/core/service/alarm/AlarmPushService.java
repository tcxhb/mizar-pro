package com.tcxhb.mizar.core.service.alarm;

import com.tcxhb.mizar.core.entity.AlarmMessage;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/30
 */
public interface AlarmPushService {
    /**
     * 告警推送
     * @param message
     */
    public void push(AlarmMessage message);
}
