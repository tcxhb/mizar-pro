package com.tcxhb.mizar.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * author:auto.generator
 * time: 2023-11-05
 */

@TableName(value = "mz_machine", autoResultMap = true)
@Data
public class MachineDO extends BaseDO {
    /**
     * *
     **/
    private String appName;

    /**
     * *
     **/
    private String ip;

    /**
     * *
     **/
    private Integer port;

    /**
     * *状态
     **/
    private Integer status;
    /**
     * 主动的心跳时间
     */
    private Date beatTime;

    public String httpAddress() {
        return "http://" + ip + ":" + port;
    }


    public boolean eq(String host, int port) {
        return this.getIp().equals(host) && this.getPort() == port;
    }

    public boolean online() {
        return this.status == 1;
    }
}
