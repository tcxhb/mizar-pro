package com.tcxhb.mizar.core.service.biz.impl;

import com.tcxhb.mizar.common.utils.IPUtils;
import com.tcxhb.mizar.core.config.JobServerConfig;
import com.tcxhb.mizar.core.entity.ServerNode;
import com.tcxhb.mizar.core.service.biz.ServerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/20
 */
@Component
@Slf4j
public class ServerMonitorImpl implements InitializingBean, ServerMonitor {
    @Autowired
    private JobServerConfig config;
    private ServerNode serverNode;

    @Override
    public Boolean offline() {
        String host = serverNode.getAddress();
        log.error("server-offline:" + host);
        return true;
    }

    @Override
    public ServerNode currentNode() {
        return this.serverNode;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        InetAddress address = IPUtils.getIpAddress();
        this.serverNode = new ServerNode();
        this.serverNode.setHost(address.getHostAddress());
        this.serverNode.setPort(config.getPort());
        this.serverNode.setAppName(config.getServerName());
    }
}
