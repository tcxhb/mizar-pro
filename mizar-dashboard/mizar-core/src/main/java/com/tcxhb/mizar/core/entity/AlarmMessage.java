package com.tcxhb.mizar.core.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/30
 */
@Data
public class AlarmMessage {
    private String app;
    private String msg;
    private String resource;
    private String ip;
    private Object value;


    public String message() {
        Map<String, String> map = new HashMap<>();
        map.put("machine", this.ip);
        map.put("app", this.app);
        map.put("resource", this.resource);
        if (value != null) {
            map.put("value", value.toString());
        }
        return buildMessage(map, this.msg);
    }

    private String buildMessage(Map<String, String> map, String message) {
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (StringUtils.isNotBlank(value)) {
                message = message.replace("${" + key + "}", value);
            }
        }
        return message;
    }
}
