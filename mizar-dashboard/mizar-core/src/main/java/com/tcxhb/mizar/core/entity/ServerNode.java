package com.tcxhb.mizar.core.entity;

import lombok.Data;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/21
 */
@Data
public class ServerNode {

    private String host;
    private Integer port;
    private String appName;
    private String address;

    public String getAddress() {
        if (address == null) {
            address = host + ":" + port;
        }
        return address;
    }
}
