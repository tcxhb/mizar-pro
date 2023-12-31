package com.tcxhb.mizar.common.constants.enums;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/16
 */
public enum ActionType {
    BLOCK("block", "限流"),
    ALARM("alarm", "告警");

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    private String type;
    private String name;

    ActionType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public boolean eq(String type) {
        return this.type.equals(type);
    }
}
