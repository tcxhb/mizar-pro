package com.tcxhb.mizar.agent.metric;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/27
 */
public enum EventType {
    SUCCESS(1), FAILURE(1);

    public int getType() {
        return type;
    }

    private int type;

    EventType(int type) {
        this.type = type;
    }
}
