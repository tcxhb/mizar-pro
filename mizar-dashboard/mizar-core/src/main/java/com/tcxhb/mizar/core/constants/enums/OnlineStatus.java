package com.tcxhb.mizar.core.constants.enums;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/4
 */
public enum OnlineStatus {
    offline(0, "离线"),
    online(1, "在线");

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    private Integer type;
    private String name;

    OnlineStatus(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
    public boolean eq(Integer type) {
        return this.type.equals(type);
    }
}
